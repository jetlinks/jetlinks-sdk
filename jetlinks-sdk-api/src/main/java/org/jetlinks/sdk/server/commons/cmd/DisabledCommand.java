package org.jetlinks.sdk.server.commons.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hswebframework.ezorm.core.CastUtil;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author liusq
 */
@Schema(title = "禁用", description = "禁用或注销")
public class DisabledCommand extends EnabledCommand {

    public static CommandHandler<EnabledCommand, Mono<Void>> createHandler(Function<EnabledCommand, Mono<Void>> handler) {

        return CommandHandler.of(
            () -> metadata(functionMetadata -> {
            }),
            (cmd, ignore) -> handler.apply(cmd),
            DisabledCommand::new
        );
    }

    public static CommandHandler<EnabledCommand, Mono<Void>> createHandler(Function<EnabledCommand, Mono<Void>> handler,
                                                                           Consumer<SimpleFunctionMetadata> custom) {
        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            DisabledCommand::new
        );

    }

    public static FunctionMetadata metadata(Consumer<SimpleFunctionMetadata> custom) {
        FunctionMetadata metadata = CommandMetadataResolver.resolve(DisabledCommand.class);
        custom.accept(CastUtil.cast(metadata));
        return metadata;
    }
}
