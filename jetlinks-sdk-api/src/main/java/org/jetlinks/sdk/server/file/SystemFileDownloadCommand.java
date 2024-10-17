package org.jetlinks.sdk.server.file;

import io.netty.buffer.ByteBuf;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import reactor.core.publisher.Flux;

/**
 * 下载系统中的文件.
 *
 * @author zhangji 2024/10/15
 * @since 2.3
 */
@Schema(description = "下载系统中的文件")
public class SystemFileDownloadCommand extends AbstractCommand<Flux<ByteBuf>, SystemFileDownloadCommand> {

    private static final long serialVersionUID = -5930467423975752546L;

    public String getFileName() {
        return (String) readable().get("fileName");
    }

    public SystemFileDownloadCommand withFileName(String name) {
        return with("fileName", name);
    }

    public static FunctionMetadata metadata(){
        return CommandMetadataResolver.resolve(SystemFileDownloadCommand.class);
    }

}
