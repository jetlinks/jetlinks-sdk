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
public class ParamInfo extends AnnotatedElementInfo {

    @Schema(description = "参数类型")
    private ClassInfo type;

    @Schema(description = "泛型信息")
    private List<ClassInfo> generics;

    public static ParamInfo of(ClassInfo type, List<ClassInfo> generics) {
        ParamInfo paramInfo = of();
        paramInfo.setType(type);
        paramInfo.setGenerics(generics);
        return paramInfo;

    }

}
