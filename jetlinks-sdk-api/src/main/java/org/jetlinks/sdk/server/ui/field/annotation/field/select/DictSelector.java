package org.jetlinks.sdk.server.ui.field.annotation.field.select;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 枚举选择器
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Selector
public @interface DictSelector {

    /**
     * 来源为字典项，配置字典项ID
     */
    String value();
}
