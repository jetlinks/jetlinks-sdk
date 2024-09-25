package org.jetlinks.sdk.server.file;

import io.netty.buffer.ByteBuf;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Schema(title = "删除文件")
public class DeleteFileCommand extends AbstractCommand<Mono<Void>, DeleteFileCommand> {
    @Schema(title = "请求体")
    public DeleteFileRequest getUrl() {
        return getOrNull("request", DeleteFileRequest.class);
    }

    public DeleteFileCommand setUrl(DeleteFileRequest request) {
        return with("request", request);
    }

    public static FunctionMetadata metadata(){
        return CommandMetadataResolver.resolve(DeleteFileCommand.class);
    }
}
