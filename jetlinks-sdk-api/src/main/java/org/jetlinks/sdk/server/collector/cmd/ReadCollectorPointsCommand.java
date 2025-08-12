package org.jetlinks.sdk.server.collector.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.sdk.server.collector.PointRuntimeInfo;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Function;


@Schema(title = "读取指定采集器点位")
public class ReadCollectorPointsCommand extends AbstractConvertCommand<Flux<PointRuntimeInfo>, ReadCollectorPointsCommand> {

    public static final String collectorIdKey = "id";
    public static final String pointIdsKey = "pointIds";

    @Schema(name = "采集器id")
    public String getCollectorId() {
        return (String) readable().get(collectorIdKey);
    }

    @Schema(name = "点位id")
    public List<String> getPointIds() {
        return ConverterUtils.convertToList(readable().get(pointIdsKey), String::valueOf);
    }

    public ReadCollectorPointsCommand withCollectorId(String id) {
        writable().put(collectorIdKey, id);
        return castSelf();
    }

    public ReadCollectorPointsCommand withPointIdList(List<String> id) {
        return with(pointIdsKey, id);
    }

}
