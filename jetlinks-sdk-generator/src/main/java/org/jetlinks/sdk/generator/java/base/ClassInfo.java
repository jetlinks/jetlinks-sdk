package org.jetlinks.sdk.generator.java.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class ClassInfo {

    @Schema(description = "类名")
    private String name;

    @Schema(description = "包路径")
    private String classPackage;

    @Schema(description = "字段信息")
    private List<FieldInfo> fields = new ArrayList<>();

    @Schema(description = "注解信息")
    private List<AnnotationInfo> annotations = new ArrayList<>();

    @Schema(description = "父类信息")
    private ClassInfo superClass;

    @Schema(description = "实现的接口信息")
    private List<ClassInfo> interfaces = new ArrayList<>();


    /**
     * 获取完整类名
     *
     * @param classInfo 类信息
     * @return 完整类名
     */
    public static String getIntactClassName(ClassInfo classInfo) {
        if (Objects.isNull(classInfo)) {
            return "";
        }
        String intactClassName = getIntactClassName(classInfo.getSuperClass());
        if (StringUtils.isNotBlank(intactClassName)) {
            return String.join(".", intactClassName, classInfo.getName());
        }
        return classInfo.getName();
    }
}
