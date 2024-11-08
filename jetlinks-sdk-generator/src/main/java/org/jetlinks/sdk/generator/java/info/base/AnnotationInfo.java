package org.jetlinks.sdk.generator.java.info.base;

import com.github.javaparser.ast.expr.AnnotationExpr;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnnotationInfo {

    @Schema(description = "注解导入路径")
    private String packagePath;

    @Schema(description = "注解表达式")
    private AnnotationExpr annotationExpr;

}
