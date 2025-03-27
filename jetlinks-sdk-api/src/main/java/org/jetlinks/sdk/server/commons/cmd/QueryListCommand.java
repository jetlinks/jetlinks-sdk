package org.jetlinks.sdk.server.commons.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.PropertyMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 查询数据列表命令
 *
 * @param <T> 数据类型
 * @author zhouhao
 * @see QueryCommand
 * @since 2.1
 */
@Schema(title = "查询列表",description = "支持动态查询条件、排序等")
public class QueryListCommand<T> extends QueryCommand<Flux<T>, QueryListCommand<T>> {

    /**
     * 请使用{@link QueryListCommand#of(Class)}创建命令
     *
     * @see QueryListCommand#of(Class)
     * @see QueryListCommand#of(Function)
     */
    @Deprecated
    public QueryListCommand() {

    }


    public static <T> FunctionMetadata metadata(Class<T> elementType) {
        return CommandMetadataResolver.resolve(QueryListCommand.class, elementType);
    }

    public static FunctionMetadata metadata(Consumer<SimpleFunctionMetadata> custom) {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        //QueryList
        metadata.setId(CommandUtils.getCommandIdByType(QueryListCommand.class));
        metadata.setName(metadata.getId());
        metadata.setDescription("可指定查询条件，排序规则等");
        metadata.setInputs(getQueryParamMetadata());
        custom.accept(metadata);
        return metadata;
    }

    public static <T> CommandHandler<QueryListCommand<T>, Flux<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<QueryListCommand<T>, Flux<T>> handler,
        ResolvableType elementType) {
        return createHandler(custom, handler, CommandUtils.createConverter(elementType));
    }

    public static <T> CommandHandler<QueryListCommand<T>, Flux<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<QueryListCommand<T>, Flux<T>> handler,
        Function<Object, T> resultConverter) {
        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            () -> of(resultConverter)
        );
    }

    @Deprecated
    public static <T> CommandHandler<QueryListCommand<T>, Flux<T>> createHandler(Consumer<SimpleFunctionMetadata> custom,
                                                                                 Function<QueryListCommand<T>, Flux<T>> handler) {
        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            QueryListCommand::new
        );

    }

    public static <T> QueryListCommand<T> of(Function<Object, T> converter) {
        return new QueryListCommand<T>().withConverter(converter);
    }

    public static <T> QueryListCommand<T> of(Class<T> type) {
        return of(CommandUtils.createConverter(ResolvableType.forClass(type)));
    }


    public static List<PropertyMetadata> getQueryParamMetadata() {
        return CommandMetadataResolver.resolveInputs(ResolvableType.forType(InputSpec.class));
    }

    protected static class InputSpec extends QueryCommand.InputSpec {


    }
}
