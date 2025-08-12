package org.jetlinks.sdk.server.collector.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.sdk.server.collector.PointData;
import org.jetlinks.sdk.server.collector.PointRuntimeInfo;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;

import java.util.List;


@Schema(title = "写入指定采集器点位")
public class WriteCollectorPointsCommand extends AbstractConvertCommand<Flux<PointRuntimeInfo>, WriteCollectorPointsCommand> {

    public static final String collectorIdKey = "id";
    public static final String pointsKey = "points";

    @Schema(name = "采集器id")
    public String getCollectorId() {
        return (String) readable().get(collectorIdKey);
    }

    @Schema(name = "点位id")
    public List<PointData> getPoints() {
        return ConverterUtils.convertToList(readable().get(pointsKey), d -> {
            if (d instanceof PointData) {
                return (PointData) d;
            }
            return FastBeanCopier.copy(d, new PointData());
        });
    }

    public WriteCollectorPointsCommand withCollectorId(String id) {
        writable().put(collectorIdKey, id);
        return castSelf();
    }

    public WriteCollectorPointsCommand withPoints(List<PointData> pointData) {
        return with(pointsKey, pointData);
    }

}
