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
public class AnnotationInfo {

    @Schema(description = "名称")
    private String name;

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

}
