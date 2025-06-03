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
@NoArgsConstructor
public class ParamInfo extends AnnotatedElementInfo {

    @Schema(description = "参数类型")
    private ClassInfo type;

    @Schema(description = "泛型信息")
    private List<ClassInfo> generics;


    public static ParamInfo of(String name,
                               List<AnnotationInfo> annotationInfos,
                               ClassInfo type,
                               List<ClassInfo> generics) {
        ParamInfo paramInfo = ParamInfo.of(type, generics);
        paramInfo.setAnnotations(annotationInfos);
        paramInfo.setName(name);
        return paramInfo;
    }

    public static ParamInfo of() {
        return new ParamInfo();
    }

}
