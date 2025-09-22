package org.jetlinks.sdk.server.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.core.DefaultExtendable;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 用户详情.
 *
 * @author zhangji 2024/2/2
 * @since 1.0.0
 */
@Getter
@Setter
public class UserDetail extends DefaultExtendable implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private String id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户类型")
    private String type;

    @Schema(description = "用户状态。1启用，0禁用")
    private Byte status;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "email")
    private String email;

    @Schema(description = "联系电话")
    private String telephone;

    @Schema(description = "头像图片地址")
    private String avatar;

    @Schema(description = "说明")
    private String description;

    @Schema(description = "创建时间")
    private long createTime;

    @Schema(description = "角色信息")
    private List<RoleInfo> roleList;

    @Schema(description = "所在机构(部门)信息")
    private List<OrganizationInfo> orgList;

    @Schema(description = "创建人ID")
    private String creatorId;

    @Schema(description = "创建人名称")
    private String creatorName;

    @Schema(description = "修改人ID")
    private String modifierId;

    @Schema(description = "修改人名称")
    private String modifierName;

    @Schema(description = "修改时间")
    private Long modifyTime;
}
