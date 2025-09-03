package org.jetlinks.sdk.server.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowledgeResourceSearchInfo {

    @Schema(title = "资源")
    private KnowledgeResourceInfo resource;

    @Schema(title = "关联理由")
    private String reason;

    @Schema(title = "关联度")
    private float confidence;

}
