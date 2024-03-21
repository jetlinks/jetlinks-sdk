package org.jetlinks.sdk.server.commons.cmd;

import org.jetlinks.core.command.AbstractCommand;
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
public class StartCommand extends AbstractCommand<Mono<Void>, StartCommand> {
    public String getId() {
        return (String) readable().get("id");
    }
    public static CommandHandler<StartCommand, Mono<Void>> createHandler(Function<StartCommand, Mono<Void>> handler) {
        return CommandHandler.of(
                () -> {
                    SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                    metadata.setId(CommandUtils.getCommandIdByType(StartCommand.class));
                    metadata.setName("启用");
                    metadata.setDescription("启用或激活");
                    metadata.setInputs(Collections.singletonList(SimplePropertyMetadata.of("id", "Id", StringType.GLOBAL)));
                    return metadata;
                },
                (cmd, ignore) -> handler.apply(cmd),
                StartCommand::new
        );

    }

}
