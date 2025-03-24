package org.jetlinks.sdk.server.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.validator.ValidatorUtils;
import org.jetlinks.sdk.server.ui.field.annotation.field.select.DevicePropertySelector;

import java.io.Serializable;

/**
 * 聚合字段.
 *
 * @author zhangji 2024/1/18
 */
@Getter
@Setter
public class DevicePropertyAggregation implements Serializable {
    private static final long serialVersionUID = -6712194239861406472L;

    @DevicePropertySelector(deviceIdKey = "id")
    @Schema(description = "属性ID")
    private String property;

    @DevicePropertySelector(deviceIdKey = "id")
    @Schema(description = "别名,默认和property一致")
    private String alias;

    @Schema(description = "聚合方式,支持(count,sum,max,min,avg)", type = "string")
    private String agg;

    public void validate() {
        ValidatorUtils.tryValidate(this);
    }
}
