package org.jetlinks.sdk.server.commons.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.StringType;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author zhouhao
 * @since 2.1
 */
@Schema(title = "根据ID查询")
public class QueryByIdCommand<T> extends OperationByIdCommand<T, QueryByIdCommand<T>> {


    /**
     * 请使用{@link DeleteByIdCommand#of(Class)}创建命令
     */
    @Deprecated
    public QueryByIdCommand() {
    }

    /**
     * 使用指定的类型创建命令,执行命令后会将执行结果转换为指定类型
     *
     * @param type 类型
     * @param <T>  类型
     * @return AddCommand
     */
    public static <T> QueryByIdCommand<T> of(Class<T> type) {
        return new QueryByIdCommand<T>()
            .withConverter(CommandUtils.createConverter(ResolvableType.forType(type)));
    }


    public static FunctionMetadata metadata(Consumer<SimpleFunctionMetadata> custom) {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        //QueryById
        metadata.setId(CommandUtils.getCommandIdByType(QueryByIdCommand.class));
        metadata.setName("根据id查询");
        metadata.setInputs(Collections.singletonList(SimplePropertyMetadata.of("id", "Id", StringType.GLOBAL)));
        custom.accept(metadata);
        return metadata;
    }

    public static <T> CommandHandler<QueryByIdCommand<Mono<T>>, Mono<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<QueryByIdCommand<Mono<T>>, Mono<T>> handler,
        ResolvableType elementType) {
        return createHandler(custom, handler, CommandUtils.createConverter(elementType));
    }

    public static <T> CommandHandler<QueryByIdCommand<Mono<T>>, Mono<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<QueryByIdCommand<Mono<T>>, Mono<T>> handler,
        Function<Object, T> resultConverter) {
        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            () -> new QueryByIdCommand<Mono<T>>().withConverter(resultConverter)
        );
    }

    @Deprecated
    public static <T> CommandHandler<QueryByIdCommand<T>, T> createHandler(Consumer<SimpleFunctionMetadata> custom,
                                                                           Function<QueryByIdCommand<T>, T> handler) {
        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            QueryByIdCommand::new
        );

    }


}
