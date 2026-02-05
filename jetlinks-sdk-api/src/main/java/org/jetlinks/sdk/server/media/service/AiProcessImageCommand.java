package org.jetlinks.sdk.server.media.service;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.annotation.command.CommandHandler;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Mono;

import java.util.Map;

public class AiProcessImageCommand extends AbstractCommand<Mono<Map<String,Object>>, AiProcessImageCommand> {

    @Schema(title = "文件id")
    public String getFileUrl() {
        return getOrNull("fileUrl", String.class);
    }

    public AiProcessImageCommand setFileUrl(String fileUrl) {
        with("fileUrl", fileUrl);
        return this;
    }
}

