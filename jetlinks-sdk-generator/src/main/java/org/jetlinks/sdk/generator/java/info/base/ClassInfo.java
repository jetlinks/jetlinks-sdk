package org.jetlinks.sdk.generator.java.info.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.sdk.generator.java.constant.ClassOrInterfaceConstant;
import org.jetlinks.sdk.generator.java.constant.ImportConstant;
import org.jetlinks.sdk.generator.java.info.EntityInfo;
import org.jetlinks.sdk.generator.java.utils.DefaultValueUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class ClassInfo {

    @Schema(description = "类名")
    private String name;

    @Schema(description = "字段信息")
    private List<FieldInfo> fields = new ArrayList<>();

    @Schema(description = "注解信息")
    private List<AnnotationInfo> annotations = new ArrayList<>();

    @Schema(description = "父类信息")
    private SuperClassOrInterfaceInfo superClass;

    @Schema(description = "实现的接口信息")
    private List<SuperClassOrInterfaceInfo> interfaces = new ArrayList<>();

    public static ClassInfo of(EntityInfo entityInfo) {
        ClassInfo classInfo = new ClassInfo();
        //添加实体类父类信息
        classInfo.setSuperClass(new SuperClassOrInterfaceInfo(ImportConstant.GENERIC_ENTITY,
                                                              Collections.singletonList(entityInfo.getPkClass()),
                                                              ClassOrInterfaceConstant.GENERIC_ENTITY));
        classInfo.setName(entityInfo.getClassSimpleName());
        //添加数据库实体类默认注解
        List<AnnotationInfo> defaultAnnotation = DefaultValueUtils
                .getDefaultAnnotation(entityInfo.getTableName(),
                                      entityInfo.getName(),
                                      entityInfo.isEnabledEntityEvent());
        classInfo.annotations.addAll(defaultAnnotation);

        // 添加实体类实现的接口
        List<SuperClassOrInterfaceInfo> interfaces = classInfo.getInterfaces();
        if (entityInfo.isRecordCreation()) {
            interfaces.add(new SuperClassOrInterfaceInfo(ImportConstant.RECORD_CREATION_ENTITY,
                                                         new ArrayList<>(),
                                                         ClassOrInterfaceConstant.RECORD_CREATION_ENTITY));
        }
        if (entityInfo.isRecordModifier()) {
            interfaces.add(new SuperClassOrInterfaceInfo(ImportConstant.RECORD_MODIFIER_ENTITY,
                                                         new ArrayList<>(),
                                                         ClassOrInterfaceConstant.RECORD_MODIFIER_ENTITY));
        }

        return classInfo;
    }
}
