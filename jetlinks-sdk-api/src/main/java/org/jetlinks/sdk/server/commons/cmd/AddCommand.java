package org.jetlinks.sdk.server.commons.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.command.GenericInputCommand;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Function;

@Schema(title = "新增数据", description = "批量新增数据")
public class AddCommand<T> extends BatchDataCommand<T, AddCommand<T>> implements GenericInputCommand<T> {

    public static FunctionMetadata metadata(Class<?> dataType) {
        return metadata(ResolvableType.forClass(dataType));
    }

    public static FunctionMetadata metadata(ResolvableType dataType) {
        return CommandMetadataResolver
            .resolve(ResolvableType.forClassWithGenerics(AddCommand.class, dataType),
                     dataType);
    }


    public static <T> CommandHandler<AddCommand<T>, Flux<T>> createHandler(Consumer<SimpleFunctionMetadata> custom,
                                                                           Function<AddCommand<T>, Flux<T>> handler) {


        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                //Add
                metadata.setId(CommandUtils.getCommandIdByType(AddCommand.class));
                metadata.setName("新增数据");
                metadata.setDescription("批量新增数据");
                custom.accept(metadata);
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            AddCommand::new
        );

    }

    protected static class InputSpec<T> extends BatchDataCommand.InputSpec<T> {

    }


}
