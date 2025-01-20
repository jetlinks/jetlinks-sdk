package org.jetlinks.sdk.server.ui.field.annotation.field;

import org.jetlinks.sdk.server.ui.field.UIComponent;
import org.jetlinks.sdk.server.ui.field.annotation.UIField;
import org.jetlinks.sdk.server.ui.field.annotation.UIOrder;
import org.jetlinks.sdk.server.ui.field.annotation.UIScope;
import org.jetlinks.sdk.server.ui.field.UIScopeType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 图片上传，使用{@link org.jetlinks.pro.io.file.FileManager}上传图片，并保存图片url字符串
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@UIField(UIComponent.IMAGE_UPLOAD)
@UIScope
@UIOrder
public @interface ImageUpload {

    @AliasFor(annotation = UIScope.class, value = "value")
    UIScopeType[] scope() default {UIScopeType.TABLE, UIScopeType.FORM, UIScopeType.DETAIL, UIScopeType.FILTER};


    @AliasFor(annotation = UIOrder.class, value = "value")
    int order() default 0;


    /**
     * 改为保存文件id
     */
    boolean saveId() default false;


}
