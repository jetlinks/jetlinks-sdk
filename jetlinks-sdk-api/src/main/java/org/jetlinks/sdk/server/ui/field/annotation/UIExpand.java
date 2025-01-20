package org.jetlinks.sdk.server.ui.field.annotation;

import java.lang.annotation.*;

/**
 * ui拓展信息
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UIExpand {

    String key();

    String textValue();

    double numberValue();

    String[] textValues();

    double[] numberValues();


}
