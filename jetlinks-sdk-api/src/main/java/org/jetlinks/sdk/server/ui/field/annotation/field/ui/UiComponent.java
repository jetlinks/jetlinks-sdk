package org.jetlinks.sdk.server.ui.field.annotation.field.ui;

import org.jetlinks.core.annotation.Expands;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Expands(key = UiComponent.KEY)
public @interface UiComponent {

    String KEY = "uiComponent";

    /**
     * 组件类型
     */
    String component();
}
