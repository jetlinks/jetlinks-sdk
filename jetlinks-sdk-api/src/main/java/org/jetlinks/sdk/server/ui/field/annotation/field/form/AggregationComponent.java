package org.jetlinks.sdk.server.ui.field.annotation.field.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@FormComponent(component = "aggregation")
public @interface AggregationComponent {

    Class<?> fields();
}
