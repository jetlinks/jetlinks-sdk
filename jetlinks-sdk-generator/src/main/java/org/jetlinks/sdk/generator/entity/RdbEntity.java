package org.jetlinks.sdk.generator.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gyl
 * @since 2.3
 */
@Getter
@Setter
public class RdbEntity extends Entity {

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

    // TODO: 2024/11/5 资产


}
