package org.jetlinks.sdk.server.ui.field.annotation;


import org.jetlinks.core.annotation.Expands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Expands(key = LinkMetadata.KEY)
public @interface LinkMetadata {

    String KEY = "linkMetadata";

    Class<?> linkClass();
}