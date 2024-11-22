package org.jetlinks.sdk.generator.java.base;

import com.github.javaparser.ast.Modifier;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor(staticName = "of")
public class MethodInfo {

    @Schema(description = "方法名")
    private String name;

    @Schema(description = "注解信息")
    private List<AnnotationInfo> annotations;

    @Schema(description = "参数列表")
    private List<ParamInfo> params;

    @Schema(description = "返回值")
    private ParamInfo returnParam;

    @Schema(description = "方法修饰符")
    private List<Modifier.Keyword> modifiers;
}
