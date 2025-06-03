package org.jetlinks.sdk.generator.java.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetlinks.sdk.generator.java.base.enums.Modifiers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class ClassInfo extends AnnotatedElementInfo {

    @Schema(description = "包路径")
    private String classPackage;

    @Schema(description = "字段信息")
    private List<FieldInfo> fields;

    @Schema(description = "父类信息")
    private ClassInfo superClass;

    @Schema(description = "实现的接口信息")
    private List<ClassInfo> interfaces;

    @Schema(description = "泛型信息")
    private List<ClassInfo> generics;

    @Schema(description = "方法信息")
    private List<MethodInfo> methods;

    @Schema(description = "类访问修饰符")
    private List<Modifiers> modifiers;

    @Schema(description = "类的所有导包信息")
    private List<String> importInfos;

    public static ClassInfo of(String name, String classPackage) {
        ClassInfo classInfo = of(name);
        classInfo.setClassPackage(classPackage);
        return classInfo;
    }

    public static ClassInfo of(String name) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setName(name);
        return classInfo;
    }

    public static ClassInfo of() {
        return new ClassInfo();
    }

    public ClassInfo withAnnotations(List<AnnotationInfo> annotations) {
        this.setAnnotations(this.mergeList(this.getAnnotations(), annotations));
        return this;
    }

    public ClassInfo withSuperClass(ClassInfo superClass) {
        this.setSuperClass(superClass);
        return this;
    }

    public ClassInfo withInterfaces(List<ClassInfo> interfaces) {
        this.interfaces = this.mergeList(this.interfaces, interfaces);
        return this;
    }

    public ClassInfo withGenerics(List<ClassInfo> generics) {
        this.generics = this.mergeList(this.generics, generics);
        return this;
    }

    public ClassInfo withFields(List<FieldInfo> fields) {
        this.fields = this.mergeList(this.fields, fields);
        return this;
    }

    public ClassInfo withMethods(List<MethodInfo> methods) {
        this.methods = this.mergeList(this.methods, methods);
        return this;
    }

    public ClassInfo withModifiers(List<Modifiers> modifiers) {
        this.modifiers = this.mergeList(this.modifiers, modifiers);
        return this;

    }

    public ClassInfo withImportInfos(Collection<String> importInfos) {
        this.importInfos = this.mergeList(this.importInfos, importInfos);
        return this;
    }

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

    private <T> List<T> mergeList(List<T> list, Collection<T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return list;
        }
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        list.addAll(dataList);

        return list;
    }
}
