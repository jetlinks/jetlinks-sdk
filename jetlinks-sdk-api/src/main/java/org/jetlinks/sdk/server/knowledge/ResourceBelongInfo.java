package org.jetlinks.sdk.server.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 用于描述资源所属的信息
 */
@Getter
@Setter
public class ResourceBelongInfo {

    @Schema(title = "id")
    private String id;

    @Schema(title = "类型")
    private String type;
}
