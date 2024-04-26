package org.jetlinks.sdk.server.commons.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Function;

public class SaveCommand<T> extends BatchDataCommand<T, SaveCommand<T>> {

    public static FunctionMetadata metadata(Consumer<SimpleFunctionMetadata> custom) {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        //Save
        metadata.setId(CommandUtils.getCommandIdByType(SaveCommand.class));
        metadata.setName("保存数据");
        metadata.setDescription("ID对应的数据不存在则新增，否则为修改");
        custom.accept(metadata);
        return metadata;
    }

    public static <T> CommandHandler<SaveCommand<T>, Flux<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<SaveCommand<T>, Flux<T>> handler,
        ResolvableType elementType) {
        return createHandler(custom, handler, CommandUtils.createConverter(elementType));
    }

    public static <T> CommandHandler<SaveCommand<T>, Flux<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<SaveCommand<T>, Flux<T>> handler,
        Function<Object, T> resultConverter) {
        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            () -> new SaveCommand<T>().withConverter(resultConverter)
        );
    }

    @Deprecated
    public static <T> CommandHandler<SaveCommand<T>, Flux<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<SaveCommand<T>, Flux<T>> handler) {


        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            SaveCommand::new
        );

    }

}
