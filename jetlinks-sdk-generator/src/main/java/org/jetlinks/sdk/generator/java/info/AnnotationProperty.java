package org.jetlinks.sdk.generator.java.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.sdk.generator.java.info.base.ClassInfo;

@Setter
@Getter
public class AnnotationProperty {

    @Schema(description = "属性名")
    private String name;

    @Schema(description = "属性类型")
    private ClassInfo type;

    @Schema(description = "属性值")
    private Object defaultValue;
}
