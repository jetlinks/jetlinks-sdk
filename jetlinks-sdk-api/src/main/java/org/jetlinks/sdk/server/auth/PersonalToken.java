package org.jetlinks.sdk.server.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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

    @Schema(title = "访问地址")
    private String accessUrl;

    @Schema(title = "有效期至(毫秒)")
    private long expires;

    @Schema(title = "访问令牌")
    private String accessToken;

}
