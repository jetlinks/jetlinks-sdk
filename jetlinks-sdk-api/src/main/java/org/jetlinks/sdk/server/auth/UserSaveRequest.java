package org.jetlinks.sdk.server.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户维护请求。由认证服务校验用户字段和关联权限；不用于本人资料编辑。
 * 密码仅用于请求传输，调用方不得记录完整请求。
 * @since 1.1.1
 */
@Getter
@Setter
public class UserSaveRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户信息")
    private User user;

    @Schema(description = "角色ID列表")
    private Set<String> roleIdList;

    @Schema(description = "机构ID列表")
    private Set<String> orgIdList;

    @Schema(description = "岗位ID列表")
    private Set<String> positions;

    @Schema(description = "业务应用ID列表")
    private Set<String> businessApplicationIdList;

    /** 写入用户信息，在用户详情基础上增加仅供写入的密码。 */
    @Getter
    @Setter
    public static class User extends UserDetail {
        private static final long serialVersionUID = 1L;

        @Schema(description = "密码")
        private String password;
    }
}
