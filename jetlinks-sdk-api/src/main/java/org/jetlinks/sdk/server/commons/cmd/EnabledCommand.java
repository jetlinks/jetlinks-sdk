package org.jetlinks.sdk.server.commons.cmd;

import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liusq
 * @date 2024/3/21
 */
public class EnabledCommand extends AbstractCommand<Mono<Void>, EnabledCommand> {
    public static final String PARAMETER_KEY = "idList";

    public List<String> getIds() {
        return ConverterUtils.convertToList(readable().get(PARAMETER_KEY), String::valueOf);
    }
    public static CommandHandler<EnabledCommand, Mono<Void>> createHandler(Function<EnabledCommand, Mono<Void>> handler) {
        return CommandHandler.of(
                () -> {
                    SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                    metadata.setId(CommandUtils.getCommandIdByType(EnabledCommand.class));
                    metadata.setName("启用");
                    metadata.setDescription("启用或激活");
                    metadata.setInputs(Collections.singletonList(SimplePropertyMetadata.of("idList", "id数组",
                                                                                           new ArrayType())));
                    return metadata;
                },
                (cmd, ignore) -> handler.apply(cmd),
                EnabledCommand::new
        );

    }

}
