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
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

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

        return new ByteBufBalancer(fixedLength, buffer)
            .onBackpressureBuffer()
            .doOnDiscard(ByteBuf.class, ReferenceCountUtil::safeRelease);

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

    static class ByteBufBalancerSubscriber extends BaseSubscriber<ByteBuf> {
        static final AtomicIntegerFieldUpdater<ByteBufBalancerSubscriber> COUNT = AtomicIntegerFieldUpdater
            .newUpdater(ByteBufBalancerSubscriber.class, "count");

        private final int fixedLength;

        private volatile int count;
        private final List<ByteBuf> buffer = new ArrayList<>(16);

        private final CoreSubscriber<? super ByteBuf> actual;

        ByteBufBalancerSubscriber(int fixedLength, CoreSubscriber<? super ByteBuf> actual) {
            this.actual = actual;
            this.fixedLength = count = fixedLength;
        }

        @Override
        @Nonnull
        public Context currentContext() {
            return actual.currentContext();
        }

        @Override
        protected void hookOnSubscribe(@Nonnull Subscription subscription) {
            actual.onSubscribe(this);
        }

        @Override
        protected void hookOnNext(@Nonnull ByteBuf buf) {
            int readableBytes = buf.readableBytes();

            //实际大于预期,则分片
            if (readableBytes > fixedLength) {
                try {
                    int chunk = readableBytes / fixedLength;
                    int remainder = readableBytes % fixedLength;

                    for (int i = 0; i < chunk; i++) {
                        hookOnNext0(buf.retainedSlice(i * fixedLength, fixedLength));
                    }

                    if (remainder > 0) {
                        hookOnNext0(buf.retainedSlice(readableBytes - remainder, remainder));
                    }
                } finally {
                    request(1);
                    ReferenceCountUtil.safeRelease(buf);
                }
                return;
            }

            hookOnNext0(buf);
            request(1);
        }


        protected void hookOnNext0(ByteBuf buf) {

            int readableBytes = buf.readableBytes();

            int remainder = COUNT.addAndGet(this, -readableBytes);
            if (remainder > 0) {
                buffer.add(buf);
                return;
            }
            List<ByteBuf> _temp = new ArrayList<>(buffer);
            buffer.clear();
            int sliceBytes = readableBytes + remainder;

            if (sliceBytes > 0) {
                _temp.add(buf.retainedSlice(0, sliceBytes));
            }

            int remainderLen = readableBytes - sliceBytes;
            if (remainderLen > 0) {
                ByteBuf remainderBuf = buf.retainedSlice(sliceBytes, remainderLen);
                buffer.add(remainderBuf);
            }

            COUNT.set(this, fixedLength - remainderLen);
            try {
                next(_temp);
            } finally {
                buf.release();
            }
        }

        protected void next(List<ByteBuf> buffers) {
            int size = buffers.size();
            if (size == 1) {
                actual.onNext(buffers.get(0));
            } else if (size > 1) {
                actual.onNext(
                    Unpooled
                        .compositeBuffer(buffers.size())
                        .addComponents(true, buffers)
                );
            }
        }

        @Override
        protected void hookFinally(@Nonnull SignalType type) {
            if (!buffer.isEmpty()) {
                for (ByteBuf byteBuf : buffer) {
                    ReferenceCountUtil.safeRelease(byteBuf);
                }
                buffer.clear();
            }
        }

        @Override
        protected void hookOnComplete() {
            if (!buffer.isEmpty()) {
                next(new ArrayList<>(buffer));
                buffer.clear();
            }
            actual.onComplete();
        }

        @Override
        protected void hookOnError(@Nonnull Throwable throwable) {
            actual.onError(throwable);
        }
    }
}
