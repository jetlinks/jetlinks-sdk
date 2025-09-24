package org.jetlinks.sdk.server.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.*;
import reactor.util.context.Context;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

import reactor.util.concurrent.Queues;
import reactor.core.publisher.Operators;

public class ByteBufUtils {
    /**
     * 对文件内容进行切割
     *
     * @param data         文件内容
     * @param maxChunkSize 最大切割大小
     * @return 切割后的内容
     */
    public static Flux<ByteBuf> splitByteBuf(ByteBuf data, int maxChunkSize) {
        int length = data.readableBytes();
        if (length <= maxChunkSize) {
            return Flux.just(data);
        }
        return Flux.
            generate(
                () -> data,
                (buf, sink) -> {
                    int readableBytes = buf.readableBytes();
                    if (readableBytes == 0) {
                        sink.complete();
                        return buf;
                    } else if (readableBytes > maxChunkSize) {
                        sink.next(
                            buf.retainedSlice(buf.readerIndex(), maxChunkSize)
                        );
                        return buf.readerIndex(buf.readerIndex() + maxChunkSize);
                    } else {
                        sink.next(
                            buf.retainedSlice(buf.readerIndex(), buf.readableBytes())
                        );
                        sink.complete();
                        return buf.readerIndex(buf.readableBytes());
                    }
                },
                ReferenceCountUtil::safeRelease);
    }

    public static int computeBalanceEachSize(long fileLength, int lengthEachPart) {
        if (fileLength == 0) {
            return lengthEachPart;
        }
        //平均份数
        long parts = fileLength / lengthEachPart;
        //每一份的实际数量
        int eachSize = parts == 0 ? (int) fileLength : (int) (fileLength / parts);
        //余数平均分配数量
        long eachRemainder = fileLength % eachSize;
        if (eachRemainder > 0) {
            eachSize += (int) Math.ceil((double) eachRemainder / parts);
        }
        return eachSize;
    }

    /**
     * 平衡ByteBuf数据流
     *
     * @param buffer      数据流
     * @param fixedLength 每个byteBuf数据长度
     * @return 新的数据流
     */
    public static Flux<ByteBuf> balanceBuffer(Flux<ByteBuf> buffer, int fixedLength) {

        return new ByteBufBalancer(fixedLength, buffer);

    }

    static class ByteBufBalancer extends FluxOperator<ByteBuf, ByteBuf> {
        final int fixedLength;

        protected ByteBufBalancer(int fixedLength, Flux<? extends ByteBuf> source) {
            super(source);
            this.fixedLength = fixedLength;
        }

        @Override
        public void subscribe(@Nonnull CoreSubscriber<? super ByteBuf> actual) {

            source.subscribe(
                new ByteBufBalancerSubscriber(fixedLength, actual)
            );
        }
    }

    static class ByteBufBalancerSubscriber implements CoreSubscriber<ByteBuf>, Subscription {
        static final AtomicIntegerFieldUpdater<ByteBufBalancerSubscriber> WIP = AtomicIntegerFieldUpdater
            .newUpdater(ByteBufBalancerSubscriber.class, "wip");
        static final AtomicLongFieldUpdater<ByteBufBalancerSubscriber> REQUESTED = AtomicLongFieldUpdater
            .newUpdater(ByteBufBalancerSubscriber.class, "requested");

        private final int fixedLength;

        private final CoreSubscriber<? super ByteBuf> actual;

        private volatile long requested;
        private volatile int wip;

        private volatile boolean done;
        private Throwable error;
        private volatile boolean cancelled;

        private Subscription s;

        private final Queue<ByteBuf> queue = Queues.<ByteBuf>unboundedMultiproducer().get();

        private final List<ByteBuf> aggregate = new ArrayList<>(16);
        private int remaining = 0;

        ByteBufBalancerSubscriber(int fixedLength, CoreSubscriber<? super ByteBuf> actual) {
            this.actual = actual;
            this.fixedLength = fixedLength;
            this.remaining = fixedLength;
        }

        @Override
        @Nonnull
        public Context currentContext() {
            return actual.currentContext();
        }

        @Override
        public void onSubscribe(@Nonnull Subscription subscription) {
            if (this.s != null) {
                subscription.cancel();
                return;
            }
            this.s = subscription;
            actual.onSubscribe(this);
            subscription.request(1);
        }

        @Override
        public void onNext(@Nonnull ByteBuf buf) {
            if (done || cancelled) {
                ReferenceCountUtil.safeRelease(buf);
                return;
            }
            queue.offer(buf);
            drain();
        }

        @Override
        public void onError(@Nonnull Throwable t) {
            error = t;
            done = true;
            drain();
        }

        @Override
        public void onComplete() {
            done = true;
            drain();
        }

        @Override
        public void request(long n) {
            if (n <= 0) {
                return;
            }
            Operators.addCap(REQUESTED, this, n);
            drain();
        }

        @Override
        public void cancel() {
            cancelled = true;
            drain();
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            final CoreSubscriber<? super ByteBuf> a = this.actual;

            for (; ; ) {
                if (cancelled) {
                    cleanup();
                    return;
                }

                long r = requested;
                long e = 0L;

                while (e != r) {
                    if (cancelled) {
                        cleanup();
                        return;
                    }
                    boolean d = done;
                    ByteBuf buf = queue.poll();
                    boolean empty = buf == null;

                    if (d && empty) {
                        Throwable ex = error;
                        if (ex != null) {
                            cleanup();
                            a.onError(ex);
                        } else {
                            if (!aggregate.isEmpty()) {
                                emitAggregate(a);
                            }
                            a.onComplete();
                        }
                        return;
                    }

                    if (empty) {
                        break;
                    }

                    // process this buf, possibly produce multiple outputs
                    boolean requestMoreAfter = true;
                    try {
                        int readable = buf.readableBytes();
                        int offset = 0;
                        for (; ; ) {
                            if (cancelled) {
                                ReferenceCountUtil.safeRelease(buf);
                                cleanup();
                                return;
                            }
                            int need = remaining;
                            if (readable - offset >= need) {
                                if (need > 0) {
                                    aggregate.add(buf.retainedSlice(offset, need));
                                }
                                offset += need;
                                readable = buf.readableBytes();
                                emitAggregate(a);
                                e++;
                                r = requested; // refresh possibly increased demand
                                if (e == r) {
                                    // if we've met demand, break processing further outputs now
                                    // any remainder from buf will be handled on next drain
                                    if (offset < buf.readableBytes()) {
                                        queue.offer(buf.retainedSlice(offset, buf.readableBytes() - offset));
                                    }
                                    ReferenceCountUtil.safeRelease(buf);
                                    requestMoreAfter = false; // do not over request when demand is satisfied
                                    break;
                                }
                                if (offset == buf.readableBytes()) {
                                    ReferenceCountUtil.safeRelease(buf);
                                    break;
                                }
                                // continue to produce next chunk from the same buf
                                continue;
                            } else {
                                int len = readable - offset;
                                if (len > 0) {
                                    aggregate.add(buf.retainedSlice(offset, len));
                                    remaining -= len;
                                }
                                ReferenceCountUtil.safeRelease(buf);
                                break;
                            }
                        }
                    } finally {
                        // request next upstream item for each polled element
                        Subscription up = this.s;
                        if (up != null && requestMoreAfter) {
                            up.request(1);
                        }
                    }
                }

                if (e != 0L) {
                    Operators.produced(REQUESTED, this, e);
                }

                if (cancelled) {
                    cleanup();
                    return;
                }

                if (done && queue.isEmpty()) {
                    Throwable ex = error;
                    if (ex != null) {
                        cleanup();
                        a.onError(ex);
                    } else {
                        if (!aggregate.isEmpty()) {
                            emitAggregate(a);
                        }
                        a.onComplete();
                    }
                    return;
                }

                missed = WIP.addAndGet(this, -missed);
                if (missed == 0) {
                    break;
                }
            }
        }

        void emitAggregate(CoreSubscriber<? super ByteBuf> a) {
            int size = aggregate.size();
            if (size == 0) {
                remaining = fixedLength;
                return;
            }
            ByteBuf out;
            if (size == 1) {
                out = aggregate.get(0);
            } else {
                out = Unpooled.compositeBuffer(size).addComponents(true, new ArrayList<>(aggregate));
            }
            aggregate.clear();
            remaining = fixedLength;
            a.onNext(out);
        }

        void cleanup() {
            for (ByteBuf b : aggregate) {
                ReferenceCountUtil.safeRelease(b);
            }
            aggregate.clear();
            ByteBuf b;
            while ((b = queue.poll()) != null) {
                ReferenceCountUtil.safeRelease(b);
            }
        }
    }
}
