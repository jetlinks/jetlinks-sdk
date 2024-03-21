package org.jetlinks.sdk.server.commons.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.StringType;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.function.Function;

/**
 * @author liusq
 * @date 2024/3/21
 */
public class DisabledCommand extends EnabledCommand {

    public static CommandHandler<EnabledCommand, Mono<Void>> createHandler(Function<EnabledCommand, Mono<Void>> handler) {

        return CommandHandler.of(
                () -> {
                    SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                    metadata.setId(CommandUtils.getCommandIdByType(DisabledCommand.class));
                    metadata.setName("禁用");
                    metadata.setDescription("禁用或注销");
                    metadata.setInputs(Collections.singletonList(SimplePropertyMetadata.of("idList", "id数组",
                                                                                           StringType.GLOBAL)));

                    return metadata;
                },
                (cmd, ignore) -> handler.apply(cmd),
                DisabledCommand::new
        );
    }
}
