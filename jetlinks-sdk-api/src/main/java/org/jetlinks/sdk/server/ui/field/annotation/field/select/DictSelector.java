package org.jetlinks.sdk.server.ui.field.annotation.field.select;

import org.jetlinks.core.annotation.ui.Selector;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 枚举选择器
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Selector(type = "dict")
public @interface DictSelector {

    /**
     * 来源为字典项，配置字典项ID
     */
    String value();

    @AliasFor(annotation = Selector.class)
    boolean multiple() default false;
}
