package org.jetlinks.sdk.generator.java.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.sdk.generator.java.base.enums.Modifiers;

import java.util.List;

@Setter
@Getter
public class FieldInfo extends AnnotatedElementInfo {

    @Schema(description = "字段")
    private String id;

    @Schema(description = "字段类型")
    private ClassInfo typeClass;

    @Schema(description = "字段修饰符")
    private List<Modifiers> modifiers;

    public static FieldInfo of(String id,
                               ClassInfo typeClass,
                               List<Modifiers> modifiers,
                               List<AnnotationInfo> annotations) {
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.setId(id);
        fieldInfo.setAnnotations(annotations);
        fieldInfo.setModifiers(modifiers);
        fieldInfo.setTypeClass(typeClass);
        return fieldInfo;
    }

    public static FieldInfo copyFrom(ClassInfo classInfo) {
        return FieldInfo.of(classInfo.getName(), classInfo, classInfo.getModifiers(), classInfo.getAnnotations());
    }

    public static FieldInfo of(String id,
                               ClassInfo typeClass,
                               List<Modifiers> modifiers) {
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.setId(id);
        fieldInfo.setModifiers(modifiers);
        fieldInfo.setTypeClass(typeClass);
        return fieldInfo;
    }
}
