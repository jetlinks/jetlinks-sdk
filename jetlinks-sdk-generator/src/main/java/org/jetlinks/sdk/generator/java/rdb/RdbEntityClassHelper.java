package org.jetlinks.sdk.generator.java.rdb;

import lombok.Getter;
import org.jetlinks.sdk.generator.java.EntityClassHelper;
import org.jetlinks.sdk.generator.java.constant.ClassOrInterfaceConstant;
import org.jetlinks.sdk.generator.java.constant.ImportConstant;
import org.jetlinks.sdk.generator.java.info.EntityInfo;
import org.jetlinks.sdk.generator.java.info.RdbEntityInfo;
import org.jetlinks.sdk.generator.java.info.base.AnnotationInfo;
import org.jetlinks.sdk.generator.java.info.base.ClassInfo;
import org.jetlinks.sdk.generator.java.info.base.SuperClassOrInterfaceInfo;
import org.jetlinks.sdk.generator.java.utils.CommonOperationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class RdbEntityClassHelper implements EntityClassHelper {

    private ClassInfo classInfo;

    @Override
    public RdbEntityClassHelper initClass(EntityInfo entityInfo) {
        this.classInfo = createEntityClassInfo(entityInfo);
        return this;
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
        List<AnnotationInfo> defaultAnnotation = CommonOperationUtils
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
