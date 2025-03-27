package org.jetlinks.sdk.server.commons.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.StringType;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Mono;

import javax.activation.CommandMap;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author zhouhao
 * @since 2.1
 */
@Schema(title = "根据ID删除")
public class DeleteByIdCommand<T> extends OperationByIdCommand<T, DeleteByIdCommand<T>> {


    /**
     * 请使用{@link DeleteByIdCommand#of(Class)}创建命令
     */
    @Deprecated
    public DeleteByIdCommand() {
    }

    /**
     * 使用指定的类型创建命令,执行命令后会将执行结果转换为指定类型
     *
     * @param type 类型
     * @param <T>  类型
     * @return AddCommand
     */
    public static <T> DeleteByIdCommand<T> of(Class<T> type) {
        return new DeleteByIdCommand<T>()
            .withConverter(CommandUtils.createConverter(ResolvableType.forType(type)));
    }


    public static FunctionMetadata metadata(Consumer<SimpleFunctionMetadata> custom) {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        //DeleteById
        metadata.setId(CommandUtils.getCommandIdByType(DeleteByIdCommand.class));
        metadata.setName("根据ID删除");
        metadata.setInputs(CommandMetadataResolver.resolveInputs(ResolvableType.forType(DeleteByIdCommand.class)));
        custom.accept(metadata);
        return metadata;
    }

    public static <T> CommandHandler<DeleteByIdCommand<Mono<T>>, Mono<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<DeleteByIdCommand<Mono<T>>, Mono<T>> handler,
        ResolvableType elementType) {
        return createHandler(custom, handler, CommandUtils.createConverter(elementType));
    }

    public static <T> CommandHandler<DeleteByIdCommand<Mono<T>>, Mono<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<DeleteByIdCommand<Mono<T>>, Mono<T>> handler,
        Function<Object, T> resultConverter) {

        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            () -> new DeleteByIdCommand<Mono<T>>().withConverter(resultConverter)
        );
    }

    public static <T> CommandHandler<DeleteByIdCommand<T>, T> createHandler(Consumer<SimpleFunctionMetadata> custom,
                                                                            Function<DeleteByIdCommand<T>, T> handler) {


        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            DeleteByIdCommand::new
        );

    }


}
