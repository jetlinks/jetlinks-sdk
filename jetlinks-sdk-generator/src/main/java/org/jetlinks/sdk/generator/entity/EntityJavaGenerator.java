package org.jetlinks.sdk.generator.entity;

import org.jetlinks.sdk.generator.java.JavaGenerator;

/**
 * 实体相关代码生成器
 *
 * @author zhouhao
 * @since 1.0.1
 */
public interface EntityJavaGenerator extends JavaGenerator {

    static EntityJavaGenerator create(Entity entity) {
        // TODO: 2024/11/4 创建默认实现
        return null;
    }

    EntityJavaGenerator addField(Column column);

}
