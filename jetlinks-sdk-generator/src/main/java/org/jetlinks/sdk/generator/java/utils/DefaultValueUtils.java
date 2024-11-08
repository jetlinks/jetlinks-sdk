package org.jetlinks.sdk.generator.java.utils;

import org.jetlinks.sdk.generator.java.constant.ImportConstant;
import org.jetlinks.sdk.generator.java.enums.EntityAnnotation;
import org.jetlinks.sdk.generator.java.enums.RdbEntityAnnotation;
import org.jetlinks.sdk.generator.java.info.base.AnnotationInfo;

import java.util.ArrayList;
import java.util.List;

public class DefaultValueUtils {


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

}
