package org.jetlinks.sdk.server.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RelationObject {

    @Schema(title = "对象id")
    private String id;

    @Schema(title = "对象类型")
    private String type;

    @Schema(title = "关系")
    private String relation;

    @Schema(title = "来源")
    private SourceType sourceType;

    @Schema(title = "关联程度", description = "0-1,值越大关联程度越深")
    private float confidence = 1f;

    @Schema(title = "补充信息")
    private Map<String, Object> expands;


}
