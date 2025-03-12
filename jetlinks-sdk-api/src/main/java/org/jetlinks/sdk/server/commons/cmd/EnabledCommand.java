package org.jetlinks.sdk.server.commons.cmd;

import org.apache.commons.collections4.CollectionUtils;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author liusq
 */
public class EnabledCommand extends OperationByIdCommand<Mono<Void>, EnabledCommand> {
    public static final String PARAMETER_KEY = "idList";

    public List<String> getIds() {
        List<String> compatible = ConverterUtils.convertToList(readable().get(PARAMETER_KEY), String::valueOf);
        return CollectionUtils.isEmpty(compatible) ? getIdList(String::valueOf) : compatible;
    }

    public static CommandHandler<EnabledCommand, Mono<Void>> createHandler(Function<EnabledCommand, Mono<Void>> handler) {
        return CommandHandler.of(
            () -> metadata(functionMetadata -> {
            }),
            (cmd, ignore) -> handler.apply(cmd),
            EnabledCommand::new
        );

    }

    public static CommandHandler<EnabledCommand, Mono<Void>> createHandler(Function<EnabledCommand, Mono<Void>> handler,
                                                                           Consumer<SimpleFunctionMetadata> custom) {
        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            EnabledCommand::new
        );

    }

    public static FunctionMetadata metadata(Consumer<SimpleFunctionMetadata> custom) {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(EnabledCommand.class));
        metadata.setName("启用");
        metadata.setDescription("启用或激活");
        metadata.setInputs(Collections.singletonList(SimplePropertyMetadata
            .of("idList", "id数组",
                new ArrayType().elementType(StringType.GLOBAL))));
        custom.accept(metadata);
        return metadata;
    }

}
