package org.jetlinks.sdk.server.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 角色信息.
 *
 * @author zhangji 2024/2/2
 * @since 1.0.0
 */
@Getter
@Setter
public class RoleInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色ID")
    private String id;

    @Schema(description = "角色名称")
    private String name;
}
