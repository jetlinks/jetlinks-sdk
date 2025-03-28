package org.jetlinks.sdk.server.ui.field.annotation.field.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@FormComponent(component = "query")
public @interface QueryComponent {

    /**
     * fields取值来源
     *
     * @return String
     */
    String key() default "";

    Class<?> fields();
}
