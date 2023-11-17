package org.jetlinks.sdk.server.file;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class UploadFileCommandTest {

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

        assertEquals(0,buf.refCnt());

    }

}