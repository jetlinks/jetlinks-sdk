package org.jetlinks.sdk.server.ui.field.annotation.field.select;

import org.jetlinks.core.annotation.ui.Selector;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体通用查询接口选择器
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Selector(type = "entity")
public @interface EntitySelector {

    /**
     * 来源为实体类
     */
    Class<?> value();

    /**
     * 配置组件的提交字段
     */
    String commitPropertyId() default "value";

    /**
     * 配置组件的展示字段
     */
    String[] displayPropertyId() default "text";

    @AliasFor(annotation = Selector.class)
    boolean multiple() default false;
}
