package org.jetlinks.sdk.server.commons;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.validator.ValidatorUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @Schema(description = "属性ID")
    @NotBlank
    private String property;

    @Schema(description = "别名,默认和property一致")
    private String alias;

    @Schema(description = "聚合方式,支持(count,sum,max,min,avg)", type = "string")
    @NotNull
    private String agg;

    public void validate() {
        ValidatorUtils.tryValidate(this);
    }
}
