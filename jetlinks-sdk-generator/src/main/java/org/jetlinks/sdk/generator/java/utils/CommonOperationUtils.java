package org.jetlinks.sdk.generator.java.utils;

import com.github.javaparser.ast.expr.AnnotationExpr;
import org.jetlinks.sdk.generator.java.constant.ImportConstant;
import org.jetlinks.sdk.generator.java.enums.EntityAnnotation;
import org.jetlinks.sdk.generator.java.enums.RdbEntityAnnotation;
import org.jetlinks.sdk.generator.java.info.base.AnnotationInfo;

import java.util.ArrayList;
import java.util.List;

public class CommonOperationUtils {


    /**
     * 数据库实体类默认注解信息
     *
     * @param tableName 表名
     * @param schema    类描述
     * @return List<AnnotationInfo>
     */
    public static List<AnnotationInfo> getDefaultAnnotation(String tableName, String schema, boolean entityEvent) {

        List<AnnotationInfo> annotationInfos = new ArrayList<>();
        annotationInfos.add(new AnnotationInfo(ImportConstant.GETTER, EntityAnnotation.getter.createAnnotation(null)));
        annotationInfos.add(new AnnotationInfo(ImportConstant.SETTER, EntityAnnotation.setter.createAnnotation(null)));
        annotationInfos.add(new AnnotationInfo(ImportConstant.TABLE, RdbEntityAnnotation.table.createAnnotation(tableName)));
        annotationInfos.add(new AnnotationInfo(ImportConstant.SCHEMA, RdbEntityAnnotation.schema.createAnnotation(schema)));

        if (entityEvent) {
            annotationInfos.add(new AnnotationInfo(ImportConstant.ENABLE_ENTITY_EVENT, RdbEntityAnnotation.enableEntityEvent.createAnnotation(null)));
        }
        return annotationInfos;
    }

    /**
     * 构建注解信息
     *
     * @param key   注解字段名
     * @param value 注解配置值
     * @return AnnotationInfo
     */
    public static AnnotationInfo createAnnotationInfo(String key, Object value) {
        switch (key) {
            case "columnSpec":
                return doCreateAnnotationInfo(RdbEntityAnnotation.column, value, ImportConstant.COLUMN);
            case "defaultValue":
                return doCreateAnnotationInfo(RdbEntityAnnotation.defaultValue, value, ImportConstant.DEFAULT_VALUE);
            case "columnTypeSpec":
                return doCreateAnnotationInfo(RdbEntityAnnotation.columnType, value, ImportConstant.COLUMN_TYPE);
            case "pattern":
                return doCreateAnnotationInfo(EntityAnnotation.pattern, value, ImportConstant.PATTERN);
            case "max":
                return doCreateAnnotationInfo(EntityAnnotation.max, value, ImportConstant.MAX);
            case "min":
                return doCreateAnnotationInfo(EntityAnnotation.min, value, ImportConstant.MIN);
            case "notnull":
                return doCreateAnnotationInfo(EntityAnnotation.notnull, value, ImportConstant.NOT_NULL);
            case "sizeSpec":
                return doCreateAnnotationInfo(EntityAnnotation.size, value, ImportConstant.SIZE);
            default:
                return null;
        }
    }

    private static AnnotationInfo doCreateAnnotationInfo(RdbEntityAnnotation rdbEntityAnnotation, Object value, String importPath) {
        return doCreateAnnotationInfo(rdbEntityAnnotation.createAnnotation(value), importPath);
    }

    private static AnnotationInfo doCreateAnnotationInfo(EntityAnnotation entityAnnotation, Object value, String importPath) {
        return doCreateAnnotationInfo(entityAnnotation.createAnnotation(value), importPath);
    }

    private static AnnotationInfo doCreateAnnotationInfo(AnnotationExpr columnAnnotation, String importPath) {
        AnnotationInfo annotationInfo = new AnnotationInfo();
        annotationInfo.setPackagePath(importPath);
        annotationInfo.setAnnotationExpr(columnAnnotation);
        return annotationInfo;
    }

}
