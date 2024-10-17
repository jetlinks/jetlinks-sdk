package org.jetlinks.sdk.server.file;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import reactor.core.publisher.Flux;

/**
 * 列出系统中目录下的所有文件.
 *
 * @author zhangji 2024/10/15
 * @since 2.3
 */
@Schema(description = "列出系统中目录下的所有文件")
public class SystemFileListCommand extends AbstractCommand<Flux<SystemFileInfo>, SystemFileListCommand> {

    private static final long serialVersionUID = -3913476257202938119L;

    public String getPath() {
        return getOrNull("path", String.class);
    }

    public SystemFileListCommand setPath(String path) {
        return with("path", path);
    }

    public static FunctionMetadata metadata(){
        return CommandMetadataResolver.resolve(SystemFileListCommand.class);
    }
}
