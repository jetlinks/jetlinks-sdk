package org.jetlinks.sdk.server.ui.field.annotation.field.select;

import org.jetlinks.core.annotation.ui.Selector;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

/**
 * 设备选择器
 */
@Target({FIELD, METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Selector(type = "device")
public @interface DeviceSelector {

    @AliasFor(annotation = Selector.class)
    boolean multiple() default false;
}
