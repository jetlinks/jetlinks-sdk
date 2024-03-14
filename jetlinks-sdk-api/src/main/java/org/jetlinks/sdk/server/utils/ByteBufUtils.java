package org.jetlinks.sdk.server.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
        return Flux.create(sink -> {
            sink.onDispose(() -> ReferenceCountUtil.safeRelease(data));

            int chunk = length / maxChunkSize;
            int remainder = length % maxChunkSize;

            for (int i = 0; i < chunk; i++) {
                sink.next(data.retainedSlice(i * maxChunkSize, maxChunkSize));
            }
            if (remainder > 0) {
                sink.next(data.retainedSlice(length - remainder, remainder));
            }
            sink.complete();

        });
    }


    /**
     * 平衡ByteBuf数据流
     *
     * @param buffer      数据流
     * @param fixedLength 每个byteBuf数据长度
     * @return 新的数据流
     */
    public static Flux<ByteBuf> balanceBuffer(Flux<ByteBuf> buffer, int fixedLength) {
        AtomicInteger count = new AtomicInteger(fixedLength);
        List<ByteBuf> temp = new ArrayList<>(16);

        return buffer
            //先尝试分片
            .flatMap(buf -> splitByteBuf(buf, fixedLength))
            .<ByteBuf>flatMap(buf -> {
                synchronized (count) {
                    int remainder = count.addAndGet(-buf.readableBytes());
                    if (remainder >= 0) {
                        temp.add(buf);
                        return Mono.empty();
                    } else {
                        List<ByteBuf> _temp = new ArrayList<>(temp);
                        temp.clear();

                        _temp.add(buf.retainedSlice(0, buf.readableBytes() + remainder));
                        int remainderFrom = buf.readableBytes() + remainder;

                        ByteBuf remainderBuf = buf.retainedSlice(remainderFrom, buf.readableBytes() - remainderFrom);
                        temp.add(remainderBuf);

                        count.set(fixedLength - remainderBuf.readableBytes());

                        return Mono.justOrEmpty(
                            Unpooled
                                .compositeBuffer(_temp.size())
                                .addComponents(true, _temp));
                    }
                }
            }).concatWith(Flux.defer(() -> temp.isEmpty()
                ? Flux.empty()
                : Flux.just(Unpooled.compositeBuffer(temp.size()).addComponents(true, temp))));
    }
}
