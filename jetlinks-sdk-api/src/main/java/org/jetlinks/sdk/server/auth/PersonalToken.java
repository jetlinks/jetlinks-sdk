package org.jetlinks.sdk.server.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class PersonalToken implements Serializable {

    private static final long serialVersionUID = 1L;

    public PersonalToken() {
    }

    @Schema(title = "令牌唯一ID")
    private String id;

    @Schema(title = "所属用户ID")
    private String ownerId;

    @Schema(title = "令牌来源类型")
    private String sourceType;

    @Schema(title = "令牌来源ID")
    private String sourceId;

    @Schema(title = "说明")
    private String description;

    @Schema(description = "重定向地址")
    private String redirect;

    @Schema(description = "认证类型,用于获取token时的二次认证.")
    private String authType;

    @Schema(description = "认证配置")
    private Map<String, Object> authConfiguration;

    @Schema(description = "授权范围")
    private GrantScope scope;

    @Schema(title = "有效期至(毫秒)")
    private long expires;

    @Schema(title = "访问地址，用于页面链接共享等")
    private String accessUrl;

    @Schema(title = "访问令牌，用于api访问等")
    private String accessToken;

}
