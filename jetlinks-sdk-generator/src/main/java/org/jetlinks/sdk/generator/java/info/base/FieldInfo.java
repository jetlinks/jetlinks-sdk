package org.jetlinks.sdk.generator.java.info.base;

import com.github.javaparser.ast.Modifier;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.sdk.generator.java.info.ColumnInfo;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class FieldInfo {

    @Schema(description = "字段")
    private String id;

    @Schema(description = "字段名称")
    private String name;

    @Schema(description = "字段类型")
    private String typeClass;

    @Schema(description = "字段修饰符")
    private List<Modifier.Keyword> modifiers;

    @Schema(description = "字段注解")
    private List<AnnotationInfo> annotations;

    public static FieldInfo of(ColumnInfo columnInfo) {
        FieldInfo fieldInfo = FastBeanCopier.copy(columnInfo, new FieldInfo());
        fieldInfo.setModifiers(Collections.singletonList(Modifier.Keyword.PRIVATE));
        return fieldInfo;
    }
}
