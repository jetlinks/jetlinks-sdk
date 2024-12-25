package org.jetlinks.sdk.generator.java.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetlinks.sdk.generator.java.base.enums.Modifiers;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MethodInfo extends AnnotatedElementInfo {

    @Schema(description = "参数列表")
    private List<ParamInfo> params;

    @Schema(description = "返回值")
    private ClassInfo returnParam;

    @Schema(description = "方法修饰符")
    private List<Modifiers> modifiers;

    @Schema(description = "方法体")
    private String body;

    public static MethodInfo of(String name,
                                List<AnnotationInfo> annotationInfos,
                                List<ParamInfo> params,
                                ClassInfo returnParam,
                                List<Modifiers> modifiers) {

        MethodInfo methodInfo = of(params, returnParam, modifiers);
        methodInfo.setName(name);
        methodInfo.setAnnotations(annotationInfos);
        return methodInfo;
    }

    public MethodInfo withBody(String body) {
        this.setBody(body);
        return this;
    }

    public static MethodInfo of(List<ParamInfo> params,
                                ClassInfo returnParam,
                                List<Modifiers> modifiers) {
        MethodInfo methodInfo = of();
        methodInfo.setModifiers(modifiers);
        methodInfo.setParams(params);
        methodInfo.setReturnParam(returnParam);
        return methodInfo;

    }

    public static MethodInfo of(String name, ClassInfo returnParam, List<Modifiers> modifiers, String body) {
        MethodInfo methodInfo = of();
        methodInfo.setReturnParam(returnParam);
        methodInfo.setModifiers(modifiers);
        methodInfo.setName(name);
        return methodInfo.withBody(body);
    }

    public static MethodInfo of() {
        return new MethodInfo();
    }
}
