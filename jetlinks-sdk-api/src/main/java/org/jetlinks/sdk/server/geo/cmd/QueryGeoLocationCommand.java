package org.jetlinks.sdk.server.geo.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.sdk.server.geo.ReGeoInfo;
import reactor.core.publisher.Mono;

@Schema(title = "查询经纬度对应位置信息")
public class QueryGeoLocationCommand extends AbstractConvertCommand<Mono<ReGeoInfo>, QueryGeoLocationCommand> {

    @Schema(title = "经度")
    public Double getLon() {
        return getOrNull("lon", Double.class);
    }

    public QueryGeoLocationCommand setLon(double lon) {
        return with("lon", lon);
    }

    @Schema(title = "纬度")
    public Double getLat() {
        return getOrNull("lat", Double.class);
    }

    public QueryGeoLocationCommand setKey(double lat) {
        return with("lat", lat);
    }


}
