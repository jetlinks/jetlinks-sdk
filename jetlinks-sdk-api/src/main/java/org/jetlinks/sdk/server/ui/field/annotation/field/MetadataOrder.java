package org.jetlinks.sdk.server.ui.field.annotation.field;

import org.jetlinks.core.annotation.Expands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Expands(key = MetadataOrder.KEY)
public @interface MetadataOrder {

    String KEY = "metadataOrder";

    int order() default 0;
}
