package org.jetlinks.sdk.server.geo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReGeoInfo {

    @Schema(description = "经度")
    private double lon;
    @Schema(description = "纬度")
    private double lat;
    @Schema(description = "地址信息")
    private String location;
    @Schema(description = "获取错误")
    private boolean error;
    @Schema(description = "获取错误信息")
    private String errorMessage;

}
