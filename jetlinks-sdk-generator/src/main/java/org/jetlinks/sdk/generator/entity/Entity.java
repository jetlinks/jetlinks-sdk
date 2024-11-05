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
public class Entity {

    @Schema(description = "名称")
    String name;

    @Schema(description = "实体类名")
    String className;

    @Schema(description = "包名")
    String packageName;

}
