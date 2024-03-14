package org.jetlinks.sdk.server.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import org.jetlinks.sdk.server.file.UploadFileCommand;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class ByteBufUtilsTest {
    @Test
    void testSplitByteBuf() {
        int size = 1024 * 8 + 5;

        ByteBuf buf = Unpooled.buffer(size);

        for (int i = 0; i < size; i++) {
            buf.writeByte(i);
        }
        CompositeByteBuf byteBufs = Unpooled.compositeBuffer();

        byte[] data = ByteBufUtil.getBytes(buf);

        UploadFileCommand
            .splitByteBuf(buf, 1024)
            .doOnNext(buffer -> byteBufs.addComponent(true, buffer.duplicate()))
            .as(StepVerifier::create)
            .expectNextCount(9)
            .verifyComplete();
        assertArrayEquals(data, ByteBufUtil.getBytes(byteBufs));
        byteBufs.release();

        assertEquals(0, buf.refCnt());

    }

    @Test
    void testBalance() {


        ByteBufUtils
            .balanceBuffer(
                Flux.range(0, 16)
                    .map(i -> Unpooled.wrappedBuffer(new byte[2048])),
                2048
            )
            //  .doOnNext(buf -> System.out.println(buf.readableBytes()))
            .as(StepVerifier::create)
            .expectNextCount(16)
            .verifyComplete();

        ByteBufUtils
            .balanceBuffer(
                Flux.range(0, 16)
                    .map(i -> Unpooled.wrappedBuffer(new byte[2050])),
                2048
            )
            .doOnNext(buf -> System.out.println(buf.readableBytes()))
            .as(StepVerifier::create)
            .expectNextCount(17)
            .verifyComplete();
    }
}