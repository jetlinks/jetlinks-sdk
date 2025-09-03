package org.jetlinks.sdk.server.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 知识库资产分类
 */
@Getter
@Setter
public class KnowledgeResourceClassifyInfo {

    @Schema(title = "id")
    private String id;

    @Schema(title = "名称")
    private String name;

    @Schema(title = "描述")
    private String description;

}
