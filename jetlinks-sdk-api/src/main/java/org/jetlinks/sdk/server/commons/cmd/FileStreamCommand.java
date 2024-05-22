package org.jetlinks.sdk.server.commons.cmd;

import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.jetlinks.core.command.Command;
import org.jetlinks.core.command.CommandSupport;
import org.jetlinks.core.utils.SerializeUtils;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * 文件流通用命令,用于执行命令并返回文件流等操作。
 *
 * @author zhouhao
 * @see FileStreamCommand#executeAndWrite(CommandSupport, ServerHttpResponse)
 * @see FileStreamCommand#wrapResponse(String, String, Flux)
 * @since 2.2
 */
public interface FileStreamCommand extends Command<Flux<FileStreamCommand.FileStream>> {

    /**
     * 是否为附件,忽略contentType,直接下载文件.
     *
     * @return 是否为附件
     * @see MediaType#APPLICATION_OCTET_STREAM
     */
    default boolean isAttachment() {
        return false;
    }

    /**
     * 包装响应结果
     *
     * @param name      文件名
     * @param mediaType 文件媒体类型
     * @param content   文件内容
     * @return 文件流
     * @see org.jetlinks.sdk.server.utils.ByteBufUtils#balanceBuffer(Flux, int)
     */
    static Flux<FileStream> wrapResponse(String name,
                                         MediaType mediaType,
                                         Flux<ByteBuf> content) {
        Objects.requireNonNull(name, "name can not be null");
        Objects.requireNonNull(mediaType, "mediaType can not be null");
        return wrapResponse(name, mediaType.toString(), content);
    }

    /**
     * 包装响应结果
     *
     * @param name      文件名
     * @param mediaType 文件媒体类型
     * @param content   文件内容
     * @return 文件流
     * @see org.jetlinks.sdk.server.utils.ByteBufUtils#balanceBuffer(Flux, int)
     */
    static Flux<FileStream> wrapResponse(String name, String mediaType, Flux<ByteBuf> content) {
        Objects.requireNonNull(name, "name can not be null");
        Objects.requireNonNull(mediaType, "mediaType can not be null");
        return Flux
            .concat(
                Mono.just(new FileStream(name, mediaType, null, null)),
                content.map(buf -> new FileStream(null, null, buf, null))
                       .doOnDiscard(FileStream.class, FileStream::release)
            );
    }

    /**
     * 使用指定的命令支持执行命令并将响应结果写出到Http响应中.
     *
     * @param support  命令支持
     * @param response ServerHttpResponse
     * @return void
     * @see ServerHttpResponse#writeWith(Publisher)
     */
    default Mono<Void> executeAndWrite(CommandSupport support, ServerHttpResponse response) {
        return support
            .execute(this)
            .switchOnFirst((signal, fileStreamFlux) -> {
                FileStream first;
                if (signal.hasValue() && (first = signal.get()) != null) {
                    MediaType mediaType = MediaType.parseMediaType(first.mediaType);
                    HttpHeaders headers = response.getHeaders();
                    headers.setContentType(mediaType);
                    //设置附件或文件流时直接下载文件
                    if (isAttachment() || mediaType.includes(MediaType.APPLICATION_OCTET_STREAM)) {
                        headers.setContentDisposition(
                            ContentDisposition
                                .attachment()
                                .filename(first.getName(), StandardCharsets.UTF_8)
                                .build()
                        );
                    }
                    return response.writeWith(
                        fileStreamFlux
                            .mapNotNull(FileStream::contentAsDataBuffer));
                }
                return fileStreamFlux.then(Mono.empty());
            })
            .then();
    }

    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    class FileStream implements Externalizable {
        @Schema(description = "文件名")
        private String name;

        @Schema(description = "媒体类型")
        private String mediaType;

        @Schema(description = "文件内容")
        private Object content;

        @Schema(description = "预留其他拓展信息")
        private Map<String, Object> others;

        public DataBuffer contentAsDataBuffer() {
            return ConverterUtils.convertDataBuffer(content);
        }

        void release() {
            ReferenceCountUtil.safeRelease(content);
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            SerializeUtils.writeNullableUTF(name, out);
            SerializeUtils.writeNullableUTF(mediaType, out);
            SerializeUtils.writeObject(content, out);
            SerializeUtils.writeObject(others, out);
        }

        @Override
        @SuppressWarnings("all")
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            name = SerializeUtils.readNullableUTF(in);
            mediaType = SerializeUtils.readNullableUTF(in);
            content = SerializeUtils.readObject(in);
            others = (Map<String, Object>) SerializeUtils.readObject(in);
        }
    }

}
