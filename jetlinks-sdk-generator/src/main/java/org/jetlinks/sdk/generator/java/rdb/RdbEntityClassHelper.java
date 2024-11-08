package org.jetlinks.sdk.generator.java.rdb;

import com.github.javaparser.ast.expr.AnnotationExpr;
import lombok.Getter;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.sdk.generator.java.EntityClassHelper;
import org.jetlinks.sdk.generator.java.constant.ClassOrInterfaceConstant;
import org.jetlinks.sdk.generator.java.constant.ImportConstant;
import org.jetlinks.sdk.generator.java.enums.RdbEntityAnnotation;
import org.jetlinks.sdk.generator.java.info.BaseColumnInfo;
import org.jetlinks.sdk.generator.java.info.EntityInfo;
import org.jetlinks.sdk.generator.java.info.RdbEntityInfo;
import org.jetlinks.sdk.generator.java.info.base.AnnotationInfo;
import org.jetlinks.sdk.generator.java.info.base.ClassInfo;
import org.jetlinks.sdk.generator.java.info.base.FieldInfo;
import org.jetlinks.sdk.generator.java.info.base.SuperClassOrInterfaceInfo;
import org.jetlinks.sdk.generator.java.utils.DefaultValueUtils;

import java.util.*;

@Getter
public class RdbEntityClassHelper implements EntityClassHelper {

    private ClassInfo classInfo;

    @Override
    public RdbEntityClassHelper initClass(EntityInfo entityInfo) {
        this.classInfo = createEntityClassInfo(entityInfo);
        return this;
    }

    @Override
    public EntityClassHelper addColumn(BaseColumnInfo info) {
        List<AnnotationInfo> annotationInfos = new ArrayList<>();
        FieldInfo fieldInfo = FieldInfo.of(info);
        classInfo.getFields().add(fieldInfo);
        Map<String, Object> columnMap = FastBeanCopier.copy(info, new HashMap<>());
        for (String key : columnMap.keySet()) {
            AnnotationInfo annotationInfo = createAnnotationInfo(key, columnMap.get(key));
            if (Objects.nonNull(annotationInfo)) {
                annotationInfos.add(annotationInfo);
            }
        }
        fieldInfo.setAnnotations(annotationInfos);
        return this;
    }

    /**
     * 构建注解信息
     *
     * @param key   注解字段名
     * @param value 注解配置值
     * @return AnnotationInfo
     */
    private AnnotationInfo createAnnotationInfo(String key, Object value) {
        switch (key) {
            case "columnSpec":
                return doCreateAnnotationInfo(RdbEntityAnnotation.column, value, ImportConstant.COLUMN);
            case "defaultValue":
                return doCreateAnnotationInfo(RdbEntityAnnotation.defaultValue, value, ImportConstant.DEFAULT_VALUE);
            case "columnTypeSpec":
                return doCreateAnnotationInfo(RdbEntityAnnotation.columnType, value, ImportConstant.COLUMN_TYPE);
            case "name":
                return doCreateAnnotationInfo(RdbEntityAnnotation.schema, value, ImportConstant.SCHEMA);
            default:
                return null;
        }
    }

    private AnnotationInfo doCreateAnnotationInfo(RdbEntityAnnotation rdbEntityAnnotation, Object value, String importPath) {
        AnnotationInfo annotationInfo = new AnnotationInfo();
        AnnotationExpr columnAnnotation = rdbEntityAnnotation.createAnnotation(value);
        annotationInfo.setPackagePath(importPath);
        annotationInfo.setAnnotationExpr(columnAnnotation);
        return annotationInfo;
    }

    @Override
    public ClassInfo getEntityClassInfo() {
        return classInfo;
    }

    public ClassInfo createEntityClassInfo(EntityInfo entityInfo) {
        RdbEntityInfo rdbEntityInfo = (RdbEntityInfo) entityInfo;
        ClassInfo classInfo = new ClassInfo();
        //添加实体类父类信息
        classInfo.setSuperClass(new SuperClassOrInterfaceInfo(ImportConstant.GENERIC_ENTITY,
                                                              Collections.singletonList(rdbEntityInfo.getPkClass()),
                                                              ClassOrInterfaceConstant.GENERIC_ENTITY));
        classInfo.setName(rdbEntityInfo.getClassSimpleName());
        //添加数据库实体类默认注解
        List<AnnotationInfo> defaultAnnotation = DefaultValueUtils
                .getDefaultAnnotation(rdbEntityInfo.getTableName(),
                                      rdbEntityInfo.getName(),
                                      rdbEntityInfo.isEnabledEntityEvent());
        classInfo.getAnnotations().addAll(defaultAnnotation);

        // 添加实体类实现的接口
        List<SuperClassOrInterfaceInfo> interfaces = classInfo.getInterfaces();
        if (rdbEntityInfo.isRecordCreation()) {
            interfaces.add(new SuperClassOrInterfaceInfo(ImportConstant.RECORD_CREATION_ENTITY,
                                                         new ArrayList<>(),
                                                         ClassOrInterfaceConstant.RECORD_CREATION_ENTITY));
        }
        if (rdbEntityInfo.isRecordModifier()) {
            interfaces.add(new SuperClassOrInterfaceInfo(ImportConstant.RECORD_MODIFIER_ENTITY,
                                                         new ArrayList<>(),
                                                         ClassOrInterfaceConstant.RECORD_MODIFIER_ENTITY));
        }

        return classInfo;
    }
}
