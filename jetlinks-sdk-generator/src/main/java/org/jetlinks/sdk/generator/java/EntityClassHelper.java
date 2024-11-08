package org.jetlinks.sdk.generator.java;

import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.sdk.generator.java.info.ColumnInfo;
import org.jetlinks.sdk.generator.java.info.EntityInfo;
import org.jetlinks.sdk.generator.java.info.base.AnnotationInfo;
import org.jetlinks.sdk.generator.java.info.base.ClassInfo;
import org.jetlinks.sdk.generator.java.info.base.FieldInfo;
import org.jetlinks.sdk.generator.java.utils.CommonOperationUtils;

import java.util.*;

public interface EntityClassHelper {

    /**
     * 添加列信息
     *
     * @param info 列信息
     * @return EntityClassHelper
     */
    default EntityClassHelper addColumn(ColumnInfo info) {
        List<AnnotationInfo> annotationInfos = new ArrayList<>();
        FieldInfo fieldInfo = FieldInfo.of(info);
        getEntityClassInfo().getFields().add(fieldInfo);
        Map<String, Object> columnMap = FastBeanCopier.copy(info, new HashMap<>());
        for (String key : columnMap.keySet()) {
            AnnotationInfo annotationInfo = CommonOperationUtils.createAnnotationInfo(key, columnMap.get(key));
            if (Objects.nonNull(annotationInfo)) {
                annotationInfos.add(annotationInfo);
            }
        }
        fieldInfo.setAnnotations(annotationInfos);
        return this;
    }

    /**
     * 初始化实体类信息
     *
     * @param entityInfo 实体信息
     * @return EntityClassHelper
     */
    EntityClassHelper initClass(EntityInfo entityInfo);

    ClassInfo getEntityClassInfo();

}
