package org.jetlinks.sdk.server.ui.field.annotation.field.select;

import org.jetlinks.core.annotation.ui.Selector;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

/**
 * 功能选择器
 */
@Target({FIELD, METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Selector(type = "function")
public @interface FunctionSelector {

    @AliasFor(annotation = Selector.class)
    boolean multiple() default false;

    /**
     * 指定设备id取值
     */
    String deviceId() default "";

    /**
     * 指定产品id取值
     */
    String productId() default "";

    /**
     * 指定设备id取值来源
     */
    String deviceIdKey() default "";

    /**
     * 指定产品id取值来源
     */
    String productIdKey() default "";

}
