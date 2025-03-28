package org.jetlinks.sdk.server.ui.field.annotation;


import java.lang.annotation.*;

/**
 * ui展示范围
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UIOrder {

    int value() default 0;
}
