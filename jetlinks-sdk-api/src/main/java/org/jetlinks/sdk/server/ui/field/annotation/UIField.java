package org.jetlinks.sdk.server.ui.field.annotation;

import org.jetlinks.core.annotation.Expands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段ui配置
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Expands(key = "uiFiled")
public @interface UIField {

    String value();


}
