package org.jetlinks.sdk.server.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.metadata.DataType;

import java.io.Serializable;

@Getter
@Setter
public class DeviceTagsInfo implements Serializable {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "设备ID")
    private String deviceId;

    @Schema(description = "标签标识")
    private String key;

    @Schema(description = "标签名称")
    private String name;

    @Schema(description = "时间戳")
    private Long timestamp;

    @Schema(description = "说明")
    private String description;

    private String formatValue;

    @Schema(description = "标签值")
    private String value;


}
