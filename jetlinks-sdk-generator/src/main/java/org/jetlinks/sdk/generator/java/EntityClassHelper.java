package org.jetlinks.sdk.generator.java;

import org.jetlinks.sdk.generator.java.info.ColumnInfo;
import org.jetlinks.sdk.generator.java.info.EntityInfo;
import org.jetlinks.sdk.generator.java.info.base.ClassInfo;

public interface EntityClassHelper {

    /**
     * 添加列信息
     *
     * @param info 列信息
     * @return EntityClassHelper
     */
    EntityClassHelper addColumn(ColumnInfo info);

    /**
     * 初始化实体类信息
     *
     * @param entityInfo 实体信息
     * @return EntityClassHelper
     */
    EntityClassHelper initClass(EntityInfo entityInfo);

    ClassInfo getEntityClassInfo();

}
