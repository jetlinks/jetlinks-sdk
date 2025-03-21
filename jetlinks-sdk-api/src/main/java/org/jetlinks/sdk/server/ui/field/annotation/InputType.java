package org.jetlinks.sdk.server.ui.field.annotation;

import org.jetlinks.core.annotation.Expands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询命令入参类型，通用查询：query，聚合查询：agg
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Expands(key = InputType.KEY)
public @interface InputType {

    String KEY = "inputType";

    /**
     * 入参类型
     *
     * @return String
     */
    String type() default "";
}
