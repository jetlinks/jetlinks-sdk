package org.jetlinks.sdk.server.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 组织信息.
 *
 * @author zhangji 2024/2/2
 * @since 1.0.0
 */
@Getter
@Setter
public class OrganizationInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "组织ID")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "上级ID")
    private String parentId;

    @Schema(description = "序号")
    private long sortIndex;

    private List<OrganizationInfo> children;
}
