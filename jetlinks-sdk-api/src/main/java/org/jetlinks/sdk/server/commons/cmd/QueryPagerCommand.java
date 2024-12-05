package org.jetlinks.sdk.server.commons.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.api.crud.entity.PagerResult;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
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
@Schema(title = "分页查询")
public class QueryPagerCommand<T> extends QueryCommand<Mono<PagerResult<T>>, QueryPagerCommand<T>> {


    public static FunctionMetadata metadata(Class<?> dataType) {
        return metadata(ResolvableType.forClass(dataType));
    }

    public static FunctionMetadata metadata(ResolvableType dataType) {
        return CommandMetadataResolver
            .resolve(ResolvableType.forClassWithGenerics(QueryPagerCommand.class, dataType));
    }

    @Deprecated
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
            converter = val -> val instanceof PagerResult
                ? (PagerResult<T>) val
                : FastBeanCopier.copy(val, new PagerResult<T>());
        } else {
            converter = value -> {
                PagerResult<Object> pagerResult = value instanceof PagerResult
                    ? (PagerResult<Object>) value
                    : FastBeanCopier.copy(value, new PagerResult<>());
                List<T> data = pagerResult
                    .getData()
                    .stream()
                    .map(d -> (T) CommandUtils.convertData(ResolvableType.forClass(type), d))
                    .collect(Collectors.toList());
                PagerResult<T> result = new PagerResult<T>(pagerResult.getTotal(), data);
                result.setPageIndex(pagerResult.getPageIndex());
                result.setPageSize(pagerResult.getPageSize());
                return result;
            };
        }
        return of(converter);
    }

    public static List<PropertyMetadata> getQueryParamMetadata() {
        return CommandMetadataResolver.resolveInputs(ResolvableType.forClass(InputSpec.class));
    }

    @Getter
    @Setter
    public abstract static class OutputSpec<T> {
        @Schema(title = "总数")
        private Integer total;

        @Schema(title = "数据")
        private List<T> data;
    }

    @Getter
    @Setter
    protected static class InputSpec extends QueryCommand.InputSpec {

        @Schema(title = "页码", description = "从0开始", defaultValue = "0")
        private Integer pageIndex;

        @Schema(title = "每页数量", defaultValue = "25")
        private Integer pageSize;

    }

}
