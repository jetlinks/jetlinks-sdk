package org.jetlinks.sdk.generator.entity;

import org.jetlinks.sdk.generator.GenerateClass;
import org.jetlinks.sdk.generator.java.JavaGenerator;

import java.util.List;

/**
 * 数据库实体相关代码生成器
 *
 * @author zhouhao
 * @since 1.0
 */
public interface RdbEntityJavaGenerator extends JavaGenerator {

    static RdbEntityJavaGenerator create(RdbEntity entity) {
        // TODO: 2024/11/4 创建默认实现
        return null;
    }

    RdbEntityJavaGenerator addField(RdbColumn rdbColumn);

    RdbEntityJavaGenerator oneToOne(RdbEntityDetail one, String idColumn, relationMethod... method);

    RdbEntityJavaGenerator oneToMany(RdbEntityDetail many, String idColumn, relationMethod... method);

    /**
     * 生成相关实体类，包含基础实体、vo实体、一对一实体、一对多实体
     */
    List<GenerateClass> generateEntity();

    GenerateClass generatesService();

    GenerateClass generatesController();


    /**
     * 根据关系生成方法
     */
    enum relationMethod {
        query,
        save,
        delete;
    }
}
