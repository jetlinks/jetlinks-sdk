package org.jetlinks.sdk.server.commons.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.StringType;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author liusq
 */
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
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(DisabledCommand.class));
        metadata.setName("禁用");
        metadata.setDescription("禁用或注销");
        metadata.setInputs(Collections.singletonList(
            SimplePropertyMetadata
                .of("idList", "id数组",
                    new ArrayType().elementType(StringType.GLOBAL))));
        custom.accept(metadata);
        return metadata;
    }
}
