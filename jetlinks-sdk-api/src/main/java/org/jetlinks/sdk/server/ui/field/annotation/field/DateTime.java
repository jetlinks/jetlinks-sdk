package org.jetlinks.sdk.server.ui.field.annotation.field;

import org.jetlinks.sdk.server.ui.field.UIComponent;
import org.jetlinks.sdk.server.ui.field.UIScopeType;
import org.jetlinks.sdk.server.ui.field.annotation.UIField;
import org.jetlinks.sdk.server.ui.field.annotation.UIOrder;
import org.jetlinks.sdk.server.ui.field.annotation.UIScope;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日期时间选择
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@UIField(UIComponent.DATE_TIME)
@UIScope
@UIOrder
public @interface DateTime {

    @AliasFor(annotation = UIScope.class, value = "value")
    UIScopeType[] scope() default {UIScopeType.TABLE, UIScopeType.FORM, UIScopeType.DETAIL, UIScopeType.FILTER};


    @AliasFor(annotation = UIOrder.class, value = "value")
    int order() default 0;


    /**
     * 日期格式化格式
     */
    String format() default "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间戳提交
     */
    boolean timestamp() default false;
}
