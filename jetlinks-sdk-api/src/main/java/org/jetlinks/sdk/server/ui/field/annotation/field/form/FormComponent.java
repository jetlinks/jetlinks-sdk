package org.jetlinks.sdk.server.ui.field.annotation.field.form;

import org.jetlinks.core.annotation.Expands;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 表单组件
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Expands(key = FormComponent.KEY)
public @interface FormComponent {

    String KEY = "formComponent";

    /**
     * 组件类型
     *
     * @return String
     */
    String component();
}
