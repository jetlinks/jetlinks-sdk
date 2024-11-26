package org.jetlinks.sdk.generator.java.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(staticName = "of")
public class AnnotationInfo extends AnnotatedElementInfo {

    @Schema(description = "类型")
    private ClassInfo type;

    @Schema(description = "属性信息")
    private List<AnnotationProperty> properties;

    public static AnnotationInfo of(String name, ClassInfo type) {
        AnnotationInfo annotationInfo = of();
        annotationInfo.setName(name);
        annotationInfo.setType(type);
        return annotationInfo;
    }

    public static AnnotationInfo of(String annotationName, ClassInfo classInfo, List<AnnotationProperty> properties) {
        AnnotationInfo annotationInfo = of(classInfo, properties);
        annotationInfo.setName(annotationName);
        return annotationInfo;
    }
}
