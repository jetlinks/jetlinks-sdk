package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.PropertyMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.IntType;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.commons.AggregationRequest;
import org.jetlinks.sdk.server.commons.cmd.OperationByIdCommand;
import org.jetlinks.sdk.server.device.DevicePropertyAggregation;
import org.jetlinks.sdk.server.ui.field.annotation.field.select.DevicePropertySelector;
import org.jetlinks.sdk.server.ui.field.annotation.field.select.DeviceSelector;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;

import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;

/**
 * 聚合查询设备属性.
 *
 * @author zhangji 2024/1/16
 */
@Schema(title = "聚合查询设备属性",description = "聚合查询设备属性数据，例如求和、求平均值等")
public class QueryPropertyAggCommand extends OperationByIdCommand<Flux<Map<String, Object>>, QueryPropertyAggCommand> {

    private static final long serialVersionUID = 1L;

    @Override
    @DeviceSelector
    @NotBlank
    @Schema(name = PARAMETER_KEY_ID, title = "ID")
    public String getId() {
        return super.getId();
    }

    @Schema(hidden = true)
    @Override
    public List<Object> getIdList() {
        return super.getIdList();
    }

    static Type columnsType = ResolvableType
        .forClassWithGenerics(List.class, DevicePropertyAggregation.class)
        .getType();

    @Schema(title = "聚合字段")
    @NotBlank
    public List<DevicePropertyAggregation> getColumns() {
        return getOrNull("columns", columnsType);
    }

    public QueryPropertyAggCommand withColumns(List<DevicePropertyAggregation> columns) {
        writable().put("columns", columns);
        return this;
    }

    @Schema(title = "查询条件")
    @NotBlank
    public AggregationRequest getQuery() {
        return getOrNull("query", AggregationRequest.class);
    }

    public QueryPropertyAggCommand withQuery(AggregationRequest query) {
        writable().put("query", query);
        return this;
    }

    @Schema(title = "简单聚合参数", description = "进行简单聚合时使用，此值为空时才使用其余参数进行聚合")
    @NotBlank
    public SimpleAggParams getSimple() {
        return getOrNull("simple", SimpleAggParams.class);
    }

    public QueryPropertyAggCommand withSimple(SimpleAggParams simple) {
        writable().put("simple", simple);
        return this;
    }

    public void generate() {
        SimpleAggParams simple = getSimple();
        if (simple != null) {
            withId(simple.getDeviceId());

            AggregationRequest request = new AggregationRequest();
            request.setFrom(new Date(simple.getFrom()));
            request.setTo(new Date(simple.getTo()));
            withQuery(request);

            DevicePropertyAggregation aggregation = new DevicePropertyAggregation();
            aggregation.setProperty(simple.getProperty());
            aggregation.setAgg(simple.getAgg());
            withColumns(Collections.singletonList(aggregation));
        }
    }


    public static CommandHandler<QueryPropertyAggCommand, Flux<Map<String, Object>>> createHandler(
        Function<QueryPropertyAggCommand, Flux<Map<String, Object>>> handler
    ) {
        return CommandHandler.of(
            QueryPropertyAggCommand::metadata,
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

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(QueryPropertyAggCommand.class);
    }


    @Getter
    @Setter
    public static class SimpleAggParams {

        @DeviceSelector
        @Schema(title = "设备id")
        private String deviceId;

        @DevicePropertySelector(deviceIdKey = "id")
        @Schema(title = "属性id", description = "设备物模型属性id")
        private String property;

        @Schema(title = "聚合方式", description = "例如count(计数),sum(求和),max(最大值),min(最小值),avg(平均值)等")
        private String agg;

        @Schema(title = "开始时间", description = "毫秒时间戳")
        private Long from;

        @Schema(title = "结束时间", description = "毫秒时间戳")
        private Long to;
    }

}
