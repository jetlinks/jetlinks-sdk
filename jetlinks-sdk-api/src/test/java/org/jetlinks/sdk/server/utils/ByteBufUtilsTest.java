package org.jetlinks.sdk.server.utils;

import io.netty.buffer.*;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetlinks.sdk.server.file.UploadFileCommand;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.util.unit.DataSize;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;
import org.reactivestreams.Subscription;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class ByteBufUtilsTest {
    @Test
    @DisplayName("splitByteBuf: 按最大块大小切分并保持数据一致性与释放")
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
    @DisplayName("balanceBuffer: 固定大小切分与按需聚合的基本行为")
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
           // .doOnNext(buf -> System.out.println(buf + " " + buf.refCnt() + "=> \n" + Arrays.toString(ByteBufUtil.getBytes(buf))))
            .as(StepVerifier::create)
            .expectNextCount(17)
            .verifyComplete();
    }

    @Test
    @Disabled
    @DisplayName("大文件流: 平衡切分后的数据一致性校验（禁用长测）")
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
    @DisplayName("computeBalanceEachSize/balanceBuffer: 多种边界下总字节一致")
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
          //  .doOnNext(e -> System.out.printf("total:%s,each:%s,parts:%s\n", total, each, e))
            .map(list -> list.stream().mapToInt(Integer::intValue).sum())
            .as(StepVerifier::create)
            .expectNext(total)
            .verifyComplete();
    }

    @Test
    @DisplayName("背压精确性: 下游请求3块时仅消费6个输入分片")
    void testBackpressureDemandExact() {
        int inputBufSize = 512;
        int fixed = 1024;
        AtomicInteger produced = new AtomicInteger();

        Flux<ByteBuf> source = Flux
            .range(0, 1000)
            .map(i -> {
                produced.incrementAndGet();
                return ByteBufAllocator.DEFAULT.buffer(inputBufSize).writeZero(inputBufSize);
            })
            .subscribeOn(Schedulers.parallel());

        StepVerifier
            .create(ByteBufUtils.balanceBuffer(source, fixed), 0)
            .thenRequest(3)
            .expectNextCount(3)
            .thenCancel()
            .verify();

        // 每2个512字节输入 -> 1个1024字节输出，3个输出应当只消费6个输入
        assertEquals(6, produced.get());
    }

    @Test
    @DisplayName("并发生产者+慢消费者: 正确性与释放，按需拉取")
    void testConcurrentProducersAndSlowConsumer() {
        int fixed = 1024;
        int total = 10_000; // 输入条目
        AtomicInteger produced = new AtomicInteger();

        // 并发生产不同大小的 ByteBuf
        Flux<ByteBuf> source = Flux
            .range(0, total)
            .parallel(4)
            .runOn(Schedulers.parallel())
            .map(i -> {
                produced.incrementAndGet();
                // 三种大小循环：600/700/800
                int sel = i % 3;
                int size = sel == 0 ? 600 : (sel == 1 ? 700 : 800);
                return ByteBufAllocator.DEFAULT.buffer(size).writeZero(size);
            })
            .sequential();

        // 计算期望输出数量
        long totalBytes = (long) (total / 3) * (600 + 700 + 800) + (total % 3 == 0 ? 0 : (total % 3 == 1 ? 600 : 600 + 700));
        long expectOut = (totalBytes + fixed - 1) / fixed;

        StepVerifier.create(ByteBufUtils.balanceBuffer(source, fixed)
                .doOnNext(ByteBuf::release), 0)
            // 慢消费者：分批一点点请求
            .thenRequest(1)
            .expectNextCount(1)
            .thenRequest(2)
            .expectNextCount(2)
            .thenRequest(expectOut - 3)
            .expectNextCount(expectOut - 3)
            .verifyComplete();

        // 并发生产应全部被消费（上游被按需拉取，不会过度消费）
        assertEquals(total, produced.get());
    }

    @Test
    @SneakyThrows
    @DisplayName("取消后释放: cancel 时释放所有未下发 ByteBuf")
    void testCancelReleasesBuffers() {
        int fixed = 1024;
        int total = 1000;
        List<ByteBuf> created = new ArrayList<>(total);

        Flux<ByteBuf> source = Flux
            .range(0, total)
            .map(i -> {
                int size = (i % 5 + 1) * 300; // 300,600,900,1200,1500
                ByteBuf b = ByteBufAllocator.DEFAULT.buffer(size).writeZero(size);
                created.add(b);
                return b;
            })
            .subscribeOn(Schedulers.parallel());

        StepVerifier.create(
                ByteBufUtils.balanceBuffer(source, fixed)
                    .doOnNext(ByteBuf::release), // 下游释放收到的
                0)
            .thenRequest(2)
            .expectNextCount(2)
            .thenCancel()
            .verify();

        Thread.sleep(1000);
        // 取消后，所有未下发的 ByteBuf 都应被 operator 清理释放
        for (ByteBuf b : created) {
            assertEquals(0, b.refCnt(), "buffer not released after cancel: " + b);
        }
    }

    @Test
    @DisplayName("并发 request 竞态: 多线程请求不丢失且无过度消费")
    void testConcurrentRequestsRace() throws Exception {
        int fixed = 1024;
        int total = 10_000;
        Flux<ByteBuf> source = Flux
            .range(0, total)
            .map(i -> ByteBufAllocator.DEFAULT.buffer(256).writeZero(256));

        Flux<ByteBuf> flux = ByteBufUtils.balanceBuffer(source, fixed);

        AtomicInteger received = new AtomicInteger();
        CountDownLatch subscribed = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(1);

        ExecutorService pool = Executors.newFixedThreadPool(4);
        final Subscription[] sub = new Subscription[1];

        flux.subscribe(new CoreSubscriber<ByteBuf>() {
            @Override
            public void onSubscribe(Subscription s) {
                sub[0] = s;
                subscribed.countDown();
                // 初始不请求，等待并发请求触发
            }

            @Override
            public void onNext(ByteBuf byteBuf) {
                received.incrementAndGet();
                byteBuf.release();
            }

            @Override
            public void onError(Throwable throwable) {
                done.countDown();
                fail(throwable);
            }

            @Override
            public void onComplete() {
                done.countDown();
            }
        });

        assertTrue(subscribed.await(5, TimeUnit.SECONDS));

        for (int t = 0; t < 4; t++) {
            pool.submit(() -> {
                for (int i = 0; i < 1000; i++) {
                    int n = 1 + ThreadLocalRandom.current().nextInt(5);
                    sub[0].request(n);
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        // 请求剩余直到完成
        sub[0].request(Long.MAX_VALUE);

        assertTrue(done.await(10, TimeUnit.SECONDS));
        assertEquals((total * 256 + fixed - 1) / fixed, received.get());
    }

    @Test
    @DisplayName("完成时残留聚合: complete 时 remaining>0 仍正确下发")
    void testCompleteWithResidualAggregation() {
        int fixed = 1024;
        // 三个 600 字节，合计 1800，预期输出 1 个完整块 1024 + 1 个残留 776
        Flux<ByteBuf> source = Flux.just(600, 600, 600)
            .map(n -> ByteBufAllocator.DEFAULT.buffer(n).writeZero(n));

        StepVerifier.create(ByteBufUtils.balanceBuffer(source, fixed)
                .doOnNext(ByteBuf::release))
            .expectNextCount(2)
            .verifyComplete();
    }

    @Test
    @DisplayName("错误终止清理: 上游 error 后抛错且释放全部缓存")
    void testErrorCleansAllBuffers() {
        int fixed = 1024;
        List<ByteBuf> created = new ArrayList<>();
        Flux<ByteBuf> source = Flux.range(0, 1000)
            .map(i -> {
                ByteBuf b = ByteBufAllocator.DEFAULT.buffer(500).writeZero(500);
                created.add(b);
                return b;
            })
            .concatWith(Mono.error(new IllegalStateException("boom")));

        StepVerifier.create(ByteBufUtils.balanceBuffer(source, fixed))
            .thenRequest(Long.MAX_VALUE)
            .thenConsumeWhile(b -> {
                b.release();
                return true;
            })
            .verifyError(IllegalStateException.class);

        for (ByteBuf b : created) {
            assertEquals(0, b.refCnt());
        }
    }

    @Test
    @DisplayName("超大单条输入切分: 多块输出数量正确且全部可释放")
    void testLargeSingleInputSplit() {
        int fixed = 1024;
        int size = 10 * 1024 * 1024 + 123; // 10MB + 123 bytes
        ByteBuf big = ByteBufAllocator.DEFAULT.buffer(size).writeZero(size);

        long expect = (size + fixed - 1L) / fixed;

        StepVerifier.create(ByteBufUtils.balanceBuffer(Flux.just(big), fixed)
                .doOnNext(ByteBuf::release))
            .expectNextCount(expect)
            .verifyComplete();
    }

    @Test
    @DisplayName("onNext 内追加 request: 重入 drain 正常，流完整结束")
    void testReentrantRequestFromOnNext() throws Exception {
        int fixed = 1024;
        int total = 5000;
        Flux<ByteBuf> source = Flux.range(0, total)
            .map(i -> ByteBufAllocator.DEFAULT.buffer(600).writeZero(600));

        Flux<ByteBuf> flux = ByteBufUtils.balanceBuffer(source, fixed);

        AtomicInteger cnt = new AtomicInteger();
        CountDownLatch subscribed = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(1);
        final Subscription[] sub = new Subscription[1];

        flux.subscribe(new CoreSubscriber<ByteBuf>() {
            @Override
            public void onSubscribe(Subscription s) {
                sub[0] = s;
                subscribed.countDown();
                s.request(1);
                s.request(9);
            }

            @Override
            public void onNext(ByteBuf byteBuf) {
                byteBuf.release();
                if (cnt.incrementAndGet() % 10 == 0) {
                    sub[0].request(5);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                done.countDown();
                fail(throwable);
            }

            @Override
            public void onComplete() {
                done.countDown();
            }
        });

        assertTrue(subscribed.await(5, TimeUnit.SECONDS));
        sub[0].request(Long.MAX_VALUE);
        assertTrue(done.await(10, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("publishOn/subscribeOn 线程迁移: 行为一致且无泄漏")
    void testPublishOnBehaviorAndReleases() {
        int fixed = 1024;
        Flux<ByteBuf> source = Flux.range(0, 5000)
            .map(i -> ByteBufAllocator.DEFAULT.buffer(300 + (i % 5) * 100).writeZero(300 + (i % 5) * 100))
            .subscribeOn(Schedulers.parallel());

        StepVerifier.create(ByteBufUtils.balanceBuffer(source, fixed)
                .publishOn(Schedulers.single())
                .doOnNext(ByteBuf::release))
            .expectNextCount((5000L * (300 + 400 + 500 + 600 + 700) / 5 + fixed - 1) / fixed)
            .verifyComplete();
    }
}