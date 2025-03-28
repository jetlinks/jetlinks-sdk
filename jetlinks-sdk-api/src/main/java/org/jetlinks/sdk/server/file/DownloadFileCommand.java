package org.jetlinks.sdk.server.file;

import io.netty.buffer.ByteBuf;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;

@Schema(title = "下载文件")
public class DownloadFileCommand extends AbstractCommand<Flux<ByteBuf>, DownloadFileCommand> {

    @Schema(title = "文件地址")
    public String getUrl() {
        return getOrNull("url", String.class);
    }

    public DownloadFileCommand setUrl(String url) {
        return with("url", url);
    }

    @Override
    public Object createResponseData(Object value) {
        return ConverterUtils.convertNettyBuffer(value);
    }

    public static FunctionMetadata metadata(){
        return CommandMetadataResolver.resolve(DownloadFileCommand.class);
    }
}
