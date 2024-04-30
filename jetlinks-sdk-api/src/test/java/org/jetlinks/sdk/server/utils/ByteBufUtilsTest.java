package org.jetlinks.sdk.server.utils;

import io.netty.buffer.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetlinks.sdk.server.file.UploadFileCommand;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.util.unit.DataSize;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.security.MessageDigest;
import java.util.Arrays;

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
                    .map(i -> ByteBufAllocator.DEFAULT.buffer().writeBytes(new byte[2048])),
                2048
            )
            .doOnNext(buf -> System.out.println(buf + " " + buf.refCnt()))
            .as(StepVerifier::create)
            .expectNextCount(16)
            .verifyComplete();

        System.out.println();
        byte[] bytes = new byte[2050];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) i;
        }
        ByteBufUtils
            .balanceBuffer(
                Flux.range(0, 16)
                    .map(i -> ByteBufAllocator.DEFAULT.buffer().writeBytes(bytes)),
                2048
            )
            .doOnNext(buf -> System.out.println(buf + " " + buf.refCnt() + "=> \n" + Arrays.toString(ByteBufUtil.getBytes(buf))))
            .as(StepVerifier::create)
            .expectNextCount(17)
            .verifyComplete();
    }

    @Test
    @Disabled
    void testBigFile() {
        MessageDigest digest =  DigestUtils.getMd5Digest();
        MessageDigest digest2 =  DigestUtils.getMd5Digest();

        Flux.range(0, (int) DataSize.ofGigabytes(1).toBytes())
            .buffer(31*1022)
            .map(list -> {
                ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(list.size());
                for (Integer i : list) {
                    buf.writeByte(i.byteValue());
                }
                digest.update(buf.nioBuffer());
                return buf;
            })
            .as(flux-> ByteBufUtils.balanceBuffer(flux, 8*1024))
            .doOnNext(buf->{
                digest2.update(buf.nioBuffer());
                buf.release();
            })
            .blockLast();

        assertArrayEquals(digest.digest(),digest2.digest());

    }

    @Test
    void testBalanceEach() {

        for (int i = 2; i < 1024; i++) {
            testBalanceEach(1024 * i, 1024 * i);
            testBalanceEach(8 * 1024 * 1054 + i, 1024 * i);
        }
    }

    void testBalanceEach(int total, int each) {
        int parts = ByteBufUtils.computeBalanceEachSize(total, each);

        ByteBufUtils
            .balanceBuffer(Flux.just(ByteBufAllocator.DEFAULT.buffer().writeBytes(new byte[total])), parts)
            .map(ref -> {
                int bytes = ref.readableBytes();
                ref.release();
                return bytes;
            })
            .collectList()
            .doOnNext(e -> System.out.printf("total:%s,each:%s,parts:%s\n", total, each, e))
            .map(list -> list.stream().mapToInt(Integer::intValue).sum())
            .as(StepVerifier::create)
            .expectNext(total)
            .verifyComplete();
    }
}