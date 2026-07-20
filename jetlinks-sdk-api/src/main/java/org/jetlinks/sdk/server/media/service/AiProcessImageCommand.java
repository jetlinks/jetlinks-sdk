package org.jetlinks.sdk.server.media.service;

import io.netty.buffer.ByteBuf;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Mono;

import java.util.Map;

public class AiProcessImageCommand extends AbstractCommand<Mono<Map<String, Object>>, AiProcessImageCommand> {

    public static final String CONTENT_KEY = "content";
    public static final String FILE_URL_KEY = "fileUrl";

    @Schema(title = "图片数据")
    public ByteBuf getContent() {
        Object image = readable().get(CONTENT_KEY);
        return image instanceof ByteBuf ? (ByteBuf) image : null;
    }

    public AiProcessImageCommand setContent(ByteBuf image) {
        return with(CONTENT_KEY, image);
    }

    @Schema(title = "文件id")
    public String getFileUrl() {
        return getOrNull(FILE_URL_KEY, String.class);
    }

    public AiProcessImageCommand setFileUrl(String fileUrl) {
        return with(FILE_URL_KEY, fileUrl);
    }
}
