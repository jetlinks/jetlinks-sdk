package org.jetlinks.sdk.server.ui.field.annotation.field;

import org.jetlinks.sdk.server.ui.field.UIComponent;
import org.jetlinks.sdk.server.ui.field.annotation.UIField;
import org.jetlinks.sdk.server.ui.field.annotation.UIOrder;
import org.jetlinks.sdk.server.ui.field.annotation.UIScope;
import org.jetlinks.sdk.server.ui.field.UIScopeType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数字输入框
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@UIField(UIComponent.INPUT_NUMBER)
@UIScope
@UIOrder
public @interface Number {

    @AliasFor(annotation = UIScope.class, value = "value")
    UIScopeType[] scope() default {UIScopeType.TABLE, UIScopeType.FORM, UIScopeType.DETAIL, UIScopeType.FILTER};


    @AliasFor(annotation = UIOrder.class, value = "value")
    int order() default 0;

    /**
     * 最小值
     */
    long minimum() default Long.MIN_VALUE;

    /**
     * 是否包含最小值
     */
    boolean exclusiveMinimum() default true;

    /**
     * 最大值
     */
    long maximum() default Long.MAX_VALUE;

    /**
     * 是否包含最大值
     */
    boolean exclusiveMaximum() default true;

}
