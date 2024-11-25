package org.jetlinks.sdk.generator.java.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.sdk.generator.java.base.enums.Modifiers;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor(staticName = "of")
public class MethodInfo extends AnnotatedElementInfo {

    @Schema(description = "参数列表")
    private List<ParamInfo> params;

    @Schema(description = "返回值")
    private ClassInfo returnParam;

    @Schema(description = "方法修饰符")
    private List<Modifiers> modifiers;
}
