package org.jetlinks.sdk.generator.java.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EntityInfo {

    @Schema(description = "名称")
    String name;

    @Schema(description = "实体类名")
    String classSimpleName;

    @Schema(description = "包名")
    String classPackage;


}
