package org.jetlinks.sdk.server.ui.field.annotation.field.select;

import org.hswebframework.web.dict.EnumDict;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 枚举选择器
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Selector
public @interface EnumSelector {

    /**
     * 来源为枚举项
     */
    Class<? extends EnumDict<?>> value();
}
