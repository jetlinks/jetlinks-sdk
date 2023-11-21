package org.jetlinks.sdk.server.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetlinks.core.command.Command;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 实现该接口,表示该命令将返回一个文件
 *
 * @author zhouhao
 * @since 2.1
 */
public interface FileResponseCommand extends Command<Mono<FileResponseCommand.FileInfo>> {


    @Getter
    @AllArgsConstructor
    class FileInfo {
        //文件名
        private String name;

        //文件类型
        private MediaType mediaType;

        //文件内容
        private Flux<DataBuffer> content;


        @SneakyThrows
        public Mono<Void> writeWith(ServerHttpResponse response) {
            response.getHeaders().setContentType(mediaType);
            response.getHeaders().setContentDisposition(
                ContentDisposition
                    .attachment()
                    .filename(name, StandardCharsets.UTF_8)
                    .build()
            );
            return response.writeWith(content);

        }

    }

}
