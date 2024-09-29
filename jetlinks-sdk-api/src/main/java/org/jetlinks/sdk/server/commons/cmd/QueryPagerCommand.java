package org.jetlinks.sdk.server.commons.cmd;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.api.crud.entity.PagerResult;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.*;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.IntType;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页查询数据指令
 *
 * @param <T> 数据类型
 * @author zhouhao
 * @see QueryCommand
 * @since 2.1
 */
@Getter
@Setter
public class QueryPagerCommand<T> extends QueryCommand<Mono<PagerResult<T>>, QueryPagerCommand<T>> {

    public static FunctionMetadata metadata(Consumer<SimpleFunctionMetadata> custom) {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        //QueryPager
        metadata.setId(CommandUtils.getCommandIdByType(QueryPagerCommand.class));
        metadata.setName(metadata.getId());
        metadata.setDescription("可指定查询条件，分页参数，排序规则等");
        metadata.setInputs(getQueryParamMetadata());
        custom.accept(metadata);
        return metadata;
    }

    public static <T> CommandHandler<QueryPagerCommand<T>, Mono<PagerResult<T>>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<QueryPagerCommand<T>, Mono<PagerResult<T>>> handler,
        ResolvableType elementType) {

        // PagerResult<T>
        ResolvableType type = ResolvableType.forClassWithGenerics(
            PagerResult.class, elementType
        );

        return createHandler(custom, handler, CommandUtils.createConverter(type));
    }

    public static <T> CommandHandler<QueryPagerCommand<T>, Mono<PagerResult<T>>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<QueryPagerCommand<T>, Mono<PagerResult<T>>> handler,
        Function<Object, PagerResult<T>> resultConverter) {
        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            () -> new QueryPagerCommand<T>().withConverter(resultConverter)
        );
    }

    @Deprecated
    public static <T> CommandHandler<QueryPagerCommand<T>, Mono<PagerResult<T>>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<QueryPagerCommand<T>, Mono<PagerResult<T>>> handler) {

        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            QueryPagerCommand::new
        );

    }

    public static DataType createOutputType(List<PropertyMetadata> properties) {
        ObjectType type = new ObjectType();
        type.setProperties(properties);
        return new ObjectType()
            .addProperty("total", "总数", IntType.GLOBAL)
            .addProperty("data", "数据", new ArrayType()
                .elementType(type));
    }

    public static <T> QueryPagerCommand<T> of(Function<Object, PagerResult<T>> converter) {
        return new QueryPagerCommand<T>().withConverter(converter);
    }

    @SuppressWarnings("unchecked")
    public static <T> QueryPagerCommand<T> of(Class<T> type) {
        Function<Object, PagerResult<T>> converter;
        if (type.isAssignableFrom(Void.class)) {
            converter = val -> (PagerResult<T>) val;
        } else {
            converter = value -> {
                PagerResult<T> pagerResult = (PagerResult<T>) value;
                List<T> data = pagerResult
                    .getData()
                    .stream()
                    .map(d -> (T) CommandUtils.convertData(ResolvableType.forClass(type), d))
                    .collect(Collectors.toList());
                pagerResult.setData(data);
                return pagerResult;
            };
        }
        return of(converter);
    }

    public static List<PropertyMetadata> getQueryParamMetadata() {
        return Arrays.asList(
            SimplePropertyMetadata.of("pageIndex", "页码,从0开始.", IntType.GLOBAL),
            SimplePropertyMetadata.of("pageSize", "每页数量", IntType.GLOBAL),
            SimplePropertyMetadata.of("terms", "查询条件", new ArrayType().elementType(
                new ObjectType()
                    .addProperty("column", "列名(属性名)", StringType.GLOBAL)
                    .addProperty("termType", "条件类型,如:like,gt,lt", StringType.GLOBAL)
                    .addProperty("value", "条件值", new ObjectType())
            )),
            SimplePropertyMetadata.of("sorts", "排序", new ArrayType().elementType(
                new ObjectType()
                    .addProperty("name", "列名(属性名)", StringType.GLOBAL)
                    .addProperty("order", "排序方式,如:asc,desc", StringType.GLOBAL)
            ))
        );
    }

}
