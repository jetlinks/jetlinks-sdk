package org.jetlinks.sdk.server.file;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import lombok.SneakyThrows;
import org.hswebframework.web.id.IDGenerator;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandSupport;
import org.jetlinks.core.utils.ConverterUtils;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class UploadFileCommand extends AbstractCommand<Mono<FileInfo>, UploadFileCommand> {

    public String getSessionId() {
        return (String) readable().get("sessionId");
    }

    public UploadFileCommand withSessionId(String sessionId) {
        return with("sessionId", sessionId);
    }

    public ByteBuf getContent() {

        Object content = readable().get("content");
        if (content instanceof ByteBuf) {
            return (ByteBuf) content;
        }
        if (content instanceof byte[]) {
            return Unpooled.wrappedBuffer(((byte[]) content));
        }
        if (content instanceof String) {
            return Unpooled.wrappedBuffer(Base64Utils.decodeFromString(((String) content)));
        }

        throw new UnsupportedOperationException("unsupported file content " + content);
    }

    public UploadFileCommand withContent(ByteBuf content) {
        return with("content", content);
    }

    public long getOffset() {
        return ConverterUtils.convert(readable().getOrDefault("offset", 0), Long.class);
    }

    public UploadFileCommand withOffset(long offset) {
        return with("offset", offset);
    }

    public boolean isSharding() {
        Object isChunk = readable().getOrDefault("sharding", false);

        return Boolean.TRUE.equals(isChunk) ||
            "true".equals(isChunk) ||
            "1".equals(isChunk);
    }

    public UploadFileCommand withSharding(long offset) {
        return with("sharding", true)
            .withOffset(offset);
    }


    public long getContentLength() {
        return ConverterUtils.convert(readable().getOrDefault("contentLength", 0), Long.class);
    }

    public UploadFileCommand withContentLength(long contentLength) {
        return with("contentLength", contentLength);
    }

    public String getFileName() {
        return (String) readable().get("fileName");
    }

    public UploadFileCommand withFileName(String name) {
        return with("fileName", name);
    }

    public String getContentType() {
        return (String) readable().get("contentType");
    }

    public UploadFileCommand withContentType(String contentType) {
        return with("contentType", contentType);
    }

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

    public static Mono<FileInfo> execute(CommandSupport cmd,
                                         ByteBuf data,
                                         int maxChunkSize,
                                         Consumer<UploadFileCommand> consumer) {
        return execute(cmd, data.readableBytes(), splitByteBuf(data, maxChunkSize), consumer);
    }

    public static Mono<FileInfo> execute(CommandSupport cmd,
                                         InputStream data,
                                         int bufferSize,
                                         Consumer<UploadFileCommand> consumer) {

        return Mono
            .defer(() -> execute0(cmd, data, bufferSize, consumer))
            .subscribeOn(Schedulers.boundedElastic());
    }


    @SneakyThrows
    @SuppressWarnings("all")
    private static Mono<FileInfo> execute0(CommandSupport cmd,
                                           InputStream data,
                                           int bufferSize,
                                           Consumer<UploadFileCommand> consumer) {

        return execute(cmd,
                       data.available(),
                       DataBufferUtils
                           .readInputStream(() -> data,
                                            new NettyDataBufferFactory(ByteBufAllocator.DEFAULT),
                                            bufferSize)
                           .map(buffer -> ((NettyDataBuffer) buffer).getNativeBuffer()),
                       consumer);
    }


    public static Mono<FileInfo> execute(CommandSupport cmd,
                                         long fileLength,
                                         Flux<ByteBuf> chunk,
                                         Consumer<UploadFileCommand> consumer) {
        String sessionId = IDGenerator.RANDOM.generate();
        AtomicLong offset = new AtomicLong();

        return chunk
            .flatMap(bytes -> {
                UploadFileCommand command = new UploadFileCommand();
                consumer.accept(command);
                ByteBuf wrap = Unpooled.unreleasableBuffer(bytes);
                return cmd
                    .execute(command
                                 .withSessionId(sessionId)
                                 .withSharding(offset.getAndAdd(bytes.readableBytes()))
                                 .withContent(wrap)
                                 .withContentLength(fileLength))
                    .doFinally(ignore -> {
                        ReferenceCountUtil.safeRelease(bytes);
                    });
            }, 8)
            .filter(f -> StringUtils.hasText(f.getAccessUrl()))
            .take(1)
            .singleOrEmpty();
    }

}