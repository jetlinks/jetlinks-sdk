package org.jetlinks.sdk.server.ui.field.annotation.field.form;

import org.jetlinks.core.annotation.Expands;
import org.springframework.core.annotation.AliasFor;

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
     * @deprecated 与 {@link Expands#key()}冲突
     */
    @AliasFor("property")
    @Deprecated
    String key() default "";

    /**
     * fields 取值的字段
     *
     * @return String
     */
    @AliasFor("key")
    String property() default "";

    /**
     * 取值的字段描述实体类
     *
     * @return Class
     */
    Class<?> fields();
}
