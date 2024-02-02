package org.jetlinks.sdk.server.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户信息.
 *
 * @author zhangji 2024/2/2
 * @since 1.0.0
 */
@Getter
@Setter
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private String id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "用户类型")
    private String type;

    @Schema(description = "用户状态。1启用，0禁用")
    private Byte status;

    @Schema(description = "创建时间")
    private long createTime;

    @Schema(description = "创建人ID")
    private String creatorId;

}
