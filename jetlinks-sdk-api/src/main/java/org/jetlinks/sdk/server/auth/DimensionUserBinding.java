package org.jetlinks.sdk.server.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 指定用户与维度实例的绑定关系。关联约束和权限由认证服务校验。
 * @since 1.1.1
 */
@Getter
@Setter
public class DimensionUserBinding implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "维度类型ID")
    private String dimensionTypeId;

    @Schema(description = "维度实例ID")
    private String dimensionId;

    @Schema(description = "关系标识")
    private String relation;

    @Schema(description = "关系名称")
    private String relationName;

    @Schema(description = "绑定特性，使用认证服务支持的特性标识")
    private String[] features;
}
