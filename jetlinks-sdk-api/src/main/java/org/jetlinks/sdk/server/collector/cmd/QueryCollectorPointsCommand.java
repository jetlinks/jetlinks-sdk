package org.jetlinks.sdk.server.collector.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.sdk.server.collector.PointRuntimeInfo;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Function;


@Schema(title = "获取指定采集器点位信息")
public class QueryCollectorPointsCommand extends AbstractConvertCommand<Flux<PointRuntimeInfo>, QueryCollectorPointsCommand> {

    public static final String collectorIdKey = "id";
    public static final String pointIdsKey = "pointIds";

    @Schema(name = "采集器id")
    public String getCollectorId() {
        return (String) readable().get(collectorIdKey);
    }

    @Schema(name = "点位id", description = "为空获取全部")
    public List<String> getPointIds() {
        return ConverterUtils.convertToList(readable().get(pointIdsKey), String::valueOf);
    }

    public QueryCollectorPointsCommand withCollectorId(String id) {
        writable().put(collectorIdKey, id);
        return castSelf();
    }

    public QueryCollectorPointsCommand withPointIdList(List<String> id) {
        return with(pointIdsKey, id);
    }

}
