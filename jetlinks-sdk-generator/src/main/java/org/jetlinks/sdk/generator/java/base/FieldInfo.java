package org.jetlinks.sdk.generator.java.base;

import com.github.javaparser.ast.Modifier;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FieldInfo {

    @Schema(description = "字段")
    private String id;

    @Schema(description = "字段名称")
    private String name;

    @Schema(description = "字段类型")
    private ClassInfo typeClass;

    @Schema(description = "字段修饰符")
    private List<Modifier.Keyword> modifiers;

    @Schema(description = "字段注解")
    private List<AnnotationInfo> annotations;

    public static FieldInfo of(String id,
                               ClassInfo typeClass,
                               List<Modifier.Keyword> modifiers,
                               List<AnnotationInfo> annotations) {
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.setId(id);
        fieldInfo.setAnnotations(annotations);
        fieldInfo.setModifiers(modifiers);
        fieldInfo.setTypeClass(typeClass);
        return fieldInfo;
    }
}
