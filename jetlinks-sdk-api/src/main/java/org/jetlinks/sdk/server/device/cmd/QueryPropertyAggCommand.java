package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
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
import org.jetlinks.sdk.server.utils.AggregationExpressionParser;
import org.jetlinks.sdk.server.utils.ObjectMappers;
import org.jetlinks.sdk.server.ui.field.annotation.field.select.DeviceSelector;
import org.springframework.core.ResolvableType;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import jakarta.validation.constraints.NotBlank;
import java.lang.reflect.Type;
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
@Schema(
    title = "聚合查询设备属性",
    description = "支持按对象数组或表达式字符串传入聚合列。为便于大模型理解与生成参数，推荐优先使用表达式字符串。",
    example = "{\"id\":\"device-1\",\"columns\":\"avg(temp) as avgTemp, max(humidity) as maxHumidity\",\"query\":{\"interval\":\"1d\",\"from\":\"now-7d\",\"to\":\"now\"}}"
)
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

    @Schema(
        title = "聚合字段",
        description = "支持2种格式: 1.对象数组; 2.表达式字符串,如: avg(temp) as avgTemp, max(humidity) as maxHumidity。传入字符串时会自动按表达式解析。为便于大模型理解与生成,推荐优先使用表达式字符串。",
        example = "avg(temp) as avgTemp, max(humidity) as maxHumidity"
    )
    @NotBlank
    public List<DevicePropertyAggregation> getColumns() {
        Object columns = readable().get("columns");
        if (columns instanceof String) {
            return parseColumns((String) columns);
        }
        return getOrNull("columns", columnsType);
    }

    public QueryPropertyAggCommand withColumns(List<DevicePropertyAggregation> columns) {
        writable().put("columns", columns);
        return this;
    }

    public QueryPropertyAggCommand withColumnsExpression(String expression) {
        writable().put("columns", expression);
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
                "聚合字段,支持对象数组或表达式字符串,如: avg(temp) as avgTemp, max(humidity) as maxHumidity",
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

    private List<DevicePropertyAggregation> parseColumns(String columns) {
        if (!StringUtils.hasText(columns)) {
            return Collections.emptyList();
        }
        String expression = columns.trim();
        if (expression.startsWith("[")) {
            return ObjectMappers.parseJsonArray(expression, DevicePropertyAggregation.class);
        }
        if (expression.startsWith("{")) {
            return Collections.singletonList(ObjectMappers.parseJson(expression, DevicePropertyAggregation.class));
        }
        return AggregationExpressionParser.parse(expression, (agg, property, alias) -> {
            DevicePropertyAggregation aggregation = new DevicePropertyAggregation();
            aggregation.setAgg(agg);
            aggregation.setProperty(property);
            if (StringUtils.hasText(alias)) {
                aggregation.setAlias(alias);
            }
            return aggregation;
        });
    }
}
