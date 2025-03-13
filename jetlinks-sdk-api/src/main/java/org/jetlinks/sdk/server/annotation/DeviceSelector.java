package org.jetlinks.sdk.server.annotation;

import org.jetlinks.core.annotation.ui.Selector;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({FIELD, METHOD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Selector(type = "device")
public @interface DeviceSelector {

    @AliasFor(annotation = Selector.class)
    boolean multiple() default false;
}
