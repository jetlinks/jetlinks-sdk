package org.jetlinks.sdk.server.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DeviceProperty implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "设备ID")
    private String deviceId;

    @Schema(description = "属性ID")
    private String property;

    @Schema(description = "状态")
    private String state;

    @Schema(description = "属性值")
    private Object value;

    @Schema(description = "数字值")
    private Object numberValue;

    @Schema(description = "格式化后的值")
    private Object formatValue;

    @Schema(description = "属性名")
    private String propertyName;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "时间戳")
    private long timestamp;

    @Schema(description = "创建时间")
    private long createTime;


}
