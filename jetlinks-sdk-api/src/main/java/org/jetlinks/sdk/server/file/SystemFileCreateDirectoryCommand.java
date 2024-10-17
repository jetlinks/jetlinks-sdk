package org.jetlinks.sdk.server.file;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 创建系统中的文件目录.
 *
 * @author zhangji 2024/10/15
 * @since 2.3
 */
@Schema(description = "创建系统中的文件目录")
public class SystemFileCreateDirectoryCommand extends AbstractCommand<Mono<Void>, SystemFileCreateDirectoryCommand> {

    private static final long serialVersionUID = -3881327664570813653L;

    public String getPath() {
        return getOrNull("path", String.class);
    }

    public SystemFileCreateDirectoryCommand setPath(String path) {
        return with("path", path);
    }

    public static FunctionMetadata metadata(){
        return CommandMetadataResolver.resolve(SystemFileCreateDirectoryCommand.class);
    }
}
