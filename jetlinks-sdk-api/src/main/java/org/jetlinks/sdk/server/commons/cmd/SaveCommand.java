package org.jetlinks.sdk.server.commons.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Function;

@Schema(title = "保存数据", description = "ID对应的数据不存在则新增，否则为修改。支持批量保存。", example = "{\"data\":[  ]}")
public class SaveCommand<T> extends BatchDataCommand<T, SaveCommand<T>> {

    /**
     * 请使用{@link SaveCommand#of(Class)}创建命令
     */
    @Deprecated
    public SaveCommand() {
    }

    public SaveCommand(Class<T> type) {
        withConverter(CommandUtils.createConverter(ResolvableType.forClass(type)));
    }

    public SaveCommand(Function<Object, T> converter) {
        withConverter(converter);
    }

    public static <T> SaveCommand<T> of(Class<T> type) {
        return of(CommandUtils.createConverter(ResolvableType.forClass(type)));
    }

    public static <T> SaveCommand<T> of(Function<Object, T> converter) {
        return new SaveCommand<T>().withConverter(converter);
    }

    public static FunctionMetadata metadata(Class<?> dataType) {
        return metadata(ResolvableType.forClass(dataType));
    }

    public static FunctionMetadata metadata(ResolvableType dataType) {
        return CommandMetadataResolver
            .resolve(ResolvableType.forClassWithGenerics(SaveCommand.class, dataType),
                     dataType);
    }

    @Deprecated
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

    public static class InputSpec<T> extends BatchDataCommand.InputSpec<T> {

    }

}
