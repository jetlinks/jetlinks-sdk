package org.jetlinks.sdk.server.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 知识库资产
 */
@Getter
@Setter
public class KnowledgeResourceInfo {

    private String id;

    @Schema(title = "名称")
    private String name;

    @Schema(title = "描述")
    private String description;

    @Schema(title = "类型")
    private String type;

    @Schema(title = "分类id")
    private String classifyId;

    @Schema(title = "检索码")
    private String searchCode;

    @Schema(title = "标签")
    private Set<String> tags;

    @Schema(title = "内容")
    private Map<String, Object> content;

    @Schema(title = "关联")
    private List<RelationObject> relations;

    @Schema(title = "所属")
    private List<ResourceBelongInfo> belongAssets;


}
