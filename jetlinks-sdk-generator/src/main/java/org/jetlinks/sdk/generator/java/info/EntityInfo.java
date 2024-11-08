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

    @Schema(description = "表名")
    String tableName;

    @Schema(description = "主键类型")
    String PkClass = "String";

    @Schema(description = "是否启用实体事件")
    boolean enabledEntityEvent = false;

    @Schema(description = "记录创建信息")
    boolean recordCreation = true;

    @Schema(description = "记录修改信息")
    boolean recordModifier = false;
}
