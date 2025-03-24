package org.jetlinks.sdk.server.ui.field.annotation;

import org.jetlinks.core.annotation.Expands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({FIELD, METHOD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Expands(key = InputProperties.KEY)
public @interface InputProperties {
    String KEY = "inputProperties";

    /**
     * 是否必填
     *
     * @return boolean
     */
    boolean required() default false;
}
