package org.jetlinks.sdk.server.file;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * 移动系统中的文件.
 *
 * @author zhangji 2024/10/15
 * @since 2.3
 */
@Schema(description = "移动系统中的文件")
public class SystemFileMoveCommand extends AbstractCommand<Mono<Void>, SystemFileMoveCommand> {

    private static final long serialVersionUID = -8707134734260353412L;

    public String getFromPath() {
        return getOrNull("fromPath", String.class);
    }

    public SystemFileMoveCommand setFromPath(String path) {
        return with("fromPath", path);
    }

    public String getToPath() {
        return getOrNull("toPath", String.class);
    }

    public SystemFileMoveCommand setToPath(String path) {
        return with("toPath", path);
    }

    // 是否复制，复制则保留原文件
    public Boolean getCopy() {
        return Optional.ofNullable(getOrNull("copy", Boolean.class)).orElse(false);
    }

    public SystemFileMoveCommand setCopy(boolean copy) {
        return with("copy", copy);
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(SystemFileMoveCommand.class);
    }

}
