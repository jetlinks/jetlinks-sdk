package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.PropertyMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.IntType;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.commons.cmd.OperationByIdCommand;
import org.jetlinks.sdk.server.device.AggregationRequest;
import org.jetlinks.sdk.server.device.DevicePropertyAggregation;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 聚合查询设备属性.
 *
 * @author zhangji 2024/1/16
 */
public class QueryPropertyAggCommand extends OperationByIdCommand<Flux<Map<String, Object>>, QueryPropertyAggCommand> {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public List<DevicePropertyAggregation> getColumns() {
        return (List<DevicePropertyAggregation>) readable().get("columns");
    }

    public QueryPropertyAggCommand withColumns(List<DevicePropertyAggregation> columns) {
        writable().put("columns", columns);
        return this;
    }

    public AggregationRequest getQuery() {
        return (AggregationRequest) readable().get("query");
    }

    public QueryPropertyAggCommand withQuery(AggregationRequest query) {
        writable().put("query", query);
        return this;
    }

    public static CommandHandler<QueryPropertyAggCommand, Flux<Map<String, Object>>> createHandler(
        Function<QueryPropertyAggCommand, Flux<Map<String, Object>>> handler
    ) {
        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(QueryPropertyAggCommand.class));
                metadata.setName("聚合查询设备属性值");
                metadata.setInputs(getQueryParamMetadata());
                metadata.setInputs(
                    Collections.singletonList(SimplePropertyMetadata.of("id", "Id", StringType.GLOBAL))
                );
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            QueryPropertyAggCommand::new
        );
    }

    public static List<PropertyMetadata> getQueryParamMetadata() {
        return Arrays.asList(
            SimplePropertyMetadata.of("id", "Id", StringType.GLOBAL),
            SimplePropertyMetadata.of(
                "columns",
                "聚合字段",
                new ArrayType()
                    .elementType(new ObjectType()
                                     .addProperty("property", "属性ID", StringType.GLOBAL)
                                     .addProperty("alias", "别名,默认和property一致", StringType.GLOBAL)
                                     .addProperty("agg", "聚合方式,count,sum,max,min,avg等", StringType.GLOBAL)
                    )),
            SimplePropertyMetadata.of(
                "query",
                "查询条件",
                new ArrayType()
                    .elementType(new ObjectType()
                                     .addProperty("interval", "时间间隔,如: 1d", StringType.GLOBAL)
                                     .addProperty("format", "时间格式,默认yyyy-MM-dd", StringType.GLOBAL)
                                     .addProperty("from", "时间从,如: 2020-09-01 00:00:00,支持表达式: now-1d", StringType.GLOBAL)
                                     .addProperty("to", "时间到,如: 2020-09-02 00:00:00,支持表达式: now-1d", StringType.GLOBAL)
                                     .addProperty("limit", "数量限制", IntType.GLOBAL)
                                     .addProperty("filter", "过滤条件,通用查询条件", new ObjectType())
                    ))
        );
    }

}
