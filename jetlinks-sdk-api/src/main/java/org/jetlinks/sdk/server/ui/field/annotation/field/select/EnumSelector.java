package org.jetlinks.sdk.server.ui.field.annotation.field.select;

import org.hswebframework.web.dict.EnumDict;
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
@Selector(type = "enum")
public @interface EnumSelector {

    /**
     * 来源为枚举项
     */
    Class<? extends EnumDict<?>> value();

    @AliasFor(annotation = Selector.class)
    boolean multiple() default false;
}
