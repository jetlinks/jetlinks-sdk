package org.jetlinks.sdk.server.file;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 删除系统中的文件.
 *
 * @author zhangji 2024/10/15
 * @since 2.3
 */
@Schema(description = "删除系统中的文件")
public class SystemFileDeleteCommand extends AbstractCommand<Mono<Void>, SystemFileDeleteCommand> {

    private static final long serialVersionUID = -2531548496061770872L;

    public String getPath() {
        return getOrNull("path", String.class);
    }

    public SystemFileDeleteCommand setPath(String path) {
        return with("path", path);
    }

    public static FunctionMetadata metadata(){
        return CommandMetadataResolver.resolve(SystemFileDeleteCommand.class);
    }
}
