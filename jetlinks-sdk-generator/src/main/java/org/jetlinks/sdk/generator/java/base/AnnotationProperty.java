package org.jetlinks.sdk.generator.java.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AnnotationProperty {

    @Schema(description = "属性名")
    private String name;

    @Schema(description = "属性类型")
    private ClassInfo type;

    @Schema(description = "属性值")
    private Object defaultValue;

    public static AnnotationProperty of(Object defaultValue) {
        AnnotationProperty annotationProperty = new AnnotationProperty();
        annotationProperty.setDefaultValue(defaultValue);
        return annotationProperty;
    }

    public static AnnotationProperty of(String name, Object defaultValue, ClassInfo type) {
        AnnotationProperty annotationProperty = of(defaultValue);
        annotationProperty.setName(name);
        annotationProperty.setType(type);
        return annotationProperty;
    }

    public static AnnotationProperty of(String name, ClassInfo type) {
        AnnotationProperty annotationProperty = new AnnotationProperty();
        annotationProperty.setName(name);
        annotationProperty.setType(type);
        return annotationProperty;
    }

    public static AnnotationProperty of() {
        return new AnnotationProperty();
    }
}
