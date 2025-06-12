package org.jetlinks.sdk.server.ui.field.annotation.field.ui;

import org.jetlinks.core.message.MessageType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@UiComponent(component = "deviceDownStream")
public @interface DeviceDownStreamComponent {

    /**
     * 设备下行消息类型
     */
    MessageType type();


}
