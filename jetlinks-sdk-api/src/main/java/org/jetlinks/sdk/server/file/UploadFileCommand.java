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
import org.jetlinks.sdk.server.utils.ByteBufUtils;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.InputStream;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * 文件上传命令,用于进行文件上传. 如设备上报文件内容时,通过此命令将文件上传到文件服务,然后存储文件访问地址到设备属性.
 *
 * <pre>{@code
 *
 *   ByteBuf filePayload = ...;
 *
 *   //分片上传文件
 *   return UploadFileCommand.execute(fileService,
 *         //文件内容
 *         filePayload,
 *         //分片大小
 *         512*1024,
 *         //设置文件名等信息
 *         command -> command.withFileName("face.jpg"))
 *       //获取最终文件访问地址
 *      .map(FileInfo::getAccessUrl);
 *
 * }</pre>
 *
 * @author zhouhao
 * @see org.jetlinks.sdk.server.SdkServices#fileService
 * @see CommandSupport
 * @see UploadFileCommand#execute(CommandSupport, ByteBuf, int, Consumer)
 * @see UploadFileCommand#execute(CommandSupport, InputStream, int, Consumer)
 * @since 1.0
 */
public class UploadFileCommand extends AbstractCommand<Mono<FileInfo>, UploadFileCommand> {

    /**
     * 获取文件id,有值时按照已预存文件信息做上传处理
     *
     * @return 文件id
     * @see PrepareUploadCommand
     */
    public String getFileId() {
        return getOrNull("id", String.class);
    }

    /**
     * 设置文件id,此时可不传其余文件基础信息
     *
     * @param id 文件id
     * @return this
     * @see PrepareUploadCommand
     */
    public UploadFileCommand withFileId(String id) {
        return with("id", id);
    }

    /**
     * @return 文件上传会话ID
     */
    public String getSessionId() {
        return (String) readable().get("sessionId");
    }

    /**
     * 设置会话ID,同一个文件的会话id应当相同.
     *
     * @param sessionId 会话ID
     * @return this
     */
    public UploadFileCommand withSessionId(String sessionId) {
        return with("sessionId", sessionId);
    }

    /**
     * 获取文件内容
     *
     * @return 文件内容
     */
    public ByteBuf getContent() {

        Object content = readable().get("content");
        if (content instanceof ByteBuf) {
            return (ByteBuf) content;
        }
        if (content instanceof byte[]) {
            return Unpooled.wrappedBuffer(((byte[]) content));
        }
        if (content instanceof String) {
            return Unpooled.wrappedBuffer(Base64.getDecoder().decode(((String) content)));
        }

        throw new UnsupportedOperationException("unsupported file content " + content);
    }

    /**
     * 设置文件内容
     *
     * @param content 文件内容
     * @return this
     */
    public UploadFileCommand withContent(ByteBuf content) {
        return with("content", content);
    }

    /**
     * 获取文件分片偏移量
     *
     * @return 偏移量
     */
    public long getOffset() {
        return ConverterUtils.convert(readable().getOrDefault("offset", 0), Long.class);
    }

    /**
     * 设置文件分片偏移量
     *
     * @param offset 偏移量
     * @return this
     */
    public UploadFileCommand withOffset(long offset) {
        return with("offset", offset);
    }

    /**
     * 是否分片上传
     *
     * @return 是否分片
     */
    public boolean isSharding() {
        Object isSharding = readable().getOrDefault("sharding", false);

        return Boolean.TRUE.equals(isSharding) ||
            "true".equals(isSharding) ||
            "1".equals(isSharding);
    }

    /**
     * 设置分片上传
     *
     * @param offset 分片偏移量
     * @return this
     */
    public UploadFileCommand withSharding(long offset) {
        return with("sharding", true)
            .withOffset(offset);
    }

    /**
     * 获取文件内容长度
     *
     * @return 文件内容长度
     */
    public long getContentLength() {
        return ConverterUtils.convert(readable().getOrDefault("contentLength", 0), Long.class);
    }

    /**
     * 设置文件内容长度
     *
     * @param contentLength 文件内容长度
     * @return this
     */
    public UploadFileCommand withContentLength(long contentLength) {
        return with("contentLength", contentLength);
    }

    /**
     * 获取文件名
     *
     * @return 文件名
     */
    public String getFileName() {
        return (String) readable().get("fileName");
    }

    /**
     * 设置文件名
     *
     * @param name 文件名
     * @return this
     */
    public UploadFileCommand withFileName(String name) {
        return with("fileName", name);
    }

    /**
     * 获取文件类型
     *
     * @return 文件类型
     */
    public String getContentType() {
        return (String) readable().get("contentType");
    }

    /**
     * 设置文件类型
     *
     * @param contentType 文件类型
     * @return this
     */
    public UploadFileCommand withContentType(String contentType) {
        return with("contentType", contentType);
    }

    /**
     * 对文件内容进行切割
     *
     * @param data         文件内容
     * @param maxChunkSize 最大切割大小
     * @return 切割后的内容
     */
    public static Flux<ByteBuf> splitByteBuf(ByteBuf data, int maxChunkSize) {
        return ByteBufUtils.splitByteBuf(data, maxChunkSize);
    }

    /**
     * 执行文件上传
     *
     * @param cmd          文件服务支持
     * @param data         文件内容
     * @param maxChunkSize 分片大小
     * @param consumer     文件上传配置
     * @return 文件信息
     */
    public static Mono<FileInfo> execute(CommandSupport cmd,
                                         ByteBuf data,
                                         int maxChunkSize,
                                         Consumer<UploadFileCommand> consumer) {
        return execute(cmd, data.readableBytes(), splitByteBuf(data, maxChunkSize), consumer);
    }

    /**
     * 执行文件上传
     *
     * @param cmd        文件服务支持
     * @param data       文件内容
     * @param bufferSize 缓冲区大小
     * @param consumer   文件上传配置
     * @return 文件信息
     */
    public static Mono<FileInfo> execute(CommandSupport cmd,
                                         InputStream data,
                                         int bufferSize,
                                         Consumer<UploadFileCommand> consumer) {

        return Mono
            .defer(() -> execute0(cmd, data, bufferSize, consumer))
            .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * 执行文件上传
     *
     * @param cmd        文件服务支持
     * @param fileLength 文件长度
     * @param chunk      文件内容
     * @param consumer   文件上传配置
     * @return 文件信息
     */
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

    /**
     * 指定分片大小,执行文件上传。
     *
     * @param cmd            文件服务支持
     * @param fileLength     文件长度
     * @param chunk          文件内容
     * @param lengthEachPart 每个分片的文件大小.
     *                       建议设置为1MB以上,因为部分云存储支持的分片大小有限制.
     * @param consumer       文件上传配置
     * @return 文件信息
     */
    public static Mono<FileInfo> execute(CommandSupport cmd,
                                         long fileLength,
                                         Flux<ByteBuf> chunk,
                                         int lengthEachPart,
                                         Consumer<UploadFileCommand> consumer) {

        lengthEachPart = ByteBufUtils.computeBalanceEachSize(fileLength, lengthEachPart);
        return execute(cmd, fileLength, ByteBufUtils.balanceBuffer(chunk, lengthEachPart), consumer);
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


}