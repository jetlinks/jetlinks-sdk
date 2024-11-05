package org.jetlinks.sdk.generator.entity;

import org.jetlinks.sdk.generator.GenerateClass;
import org.jetlinks.sdk.generator.java.JavaGenerator;

import java.util.List;

/**
 * 数据库实体相关代码生成器
 *
 * @author zhouhao
 * @since 1.0.1
 */
public interface RdbEntityJavaGenerator extends JavaGenerator {

    static RdbEntityJavaGenerator create(RdbEntity entity) {
        // TODO: 2024/11/4 创建默认实现
        return null;
    }

    RdbEntityJavaGenerator addField(RdbColumn rdbColumn);

    RdbEntityServiceJavaGenerator serviceGenerator(String commandId);

    /**
     * 生成相关实体类，包含基础实体、vo实体、一对一实体、一对多实体
     */
    List<GenerateClass> generateEntity();


    interface RdbEntityServiceJavaGenerator {

        RdbEntityServiceJavaGenerator oneToOne(RdbEntityDetail one, String idColumn, relationMethod... method);

        RdbEntityServiceJavaGenerator oneToMany(RdbEntityDetail many, String idColumn, relationMethod... method);

        GenerateClass generatesService(String packageName);

        GenerateClass generatesController(String packageName);

    }


    /**
     * 根据关系生成方法
     */
    enum relationMethod {
        query,
        save,
        delete;
    }
}
