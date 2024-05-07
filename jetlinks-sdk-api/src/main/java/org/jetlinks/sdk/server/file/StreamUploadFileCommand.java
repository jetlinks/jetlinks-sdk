package org.jetlinks.sdk.server.file;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import lombok.SneakyThrows;
import org.hswebframework.web.id.IDGenerator;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.AbstractStreamCommand;
import org.jetlinks.core.command.CommandSupport;
import org.jetlinks.core.utils.ConverterUtils;
import org.jetlinks.sdk.server.utils.ByteBufUtils;
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

/**
 * 流式文件上传命令,用于进行文件上传.
 * 如设备上报文件内容时,通过此命令将文件上传到文件服务,然后存储文件访问地址到设备属性.
 *
 * <pre>{@code
 *
 *   Flux<ByteBuf> filePayload = ...;
 *
 *   //分片上传文件
 *   return StreamUploadFileCommand.execute(fileService,
 *         //文件内容
 *         filePayload,
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
 * @see StreamUploadFileCommand
 * @see StreamUploadFileCommand#execute(CommandSupport, InputStream, int, Consumer)
 * @see StreamUploadFileCommand#execute(CommandSupport, Flux, Consumer)
 * @since 1.0
 */
public class StreamUploadFileCommand extends AbstractStreamCommand<ByteBuf, FileInfo, StreamUploadFileCommand> {

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
    public StreamUploadFileCommand withFileName(String name) {
        return with("fileName", name);
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
                                         Consumer<StreamUploadFileCommand> consumer) {

        return Mono
            .defer(() -> execute0(cmd, data, bufferSize, consumer));
    }

    /**
     * 执行文件上传
     *
     * @param cmd      文件服务支持
     * @param body    文件内容
     * @param consumer 文件上传配置
     * @return 文件信息
     */
    public static Mono<FileInfo> execute(CommandSupport cmd,
                                         Flux<ByteBuf> body,
                                         Consumer<StreamUploadFileCommand> consumer) {
        StreamUploadFileCommand command = new StreamUploadFileCommand();
        consumer.accept(command);
        command.withStream(body);
        return cmd
            .execute(command)
            .take(1)
            .singleOrEmpty();
    }

    @SneakyThrows
    @SuppressWarnings("all")
    private static Mono<FileInfo> execute0(CommandSupport cmd,
                                           InputStream data,
                                           int bufferSize,
                                           Consumer<StreamUploadFileCommand> consumer) {

        return execute(cmd,
                       DataBufferUtils
                           .readInputStream(() -> data,
                                            new NettyDataBufferFactory(ByteBufAllocator.DEFAULT),
                                            bufferSize)
                           .subscribeOn(Schedulers.boundedElastic())
                           .map(buffer -> ((NettyDataBuffer) buffer).getNativeBuffer()),
                       consumer);
    }


}