package org.jetlinks.sdk.server.annotation;

import org.hswebframework.web.api.crud.entity.GenericEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识该类是用于联查结果的 VO 类
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinVo {

    /**
     * 主表实体类
     */
    Class<?> value();
}
