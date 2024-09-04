package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.message.DeviceMessageReply;
import org.jetlinks.core.message.MessageType;
import org.jetlinks.core.metadata.types.BooleanType;
import org.jetlinks.core.metadata.types.LongType;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * 发送消息给设备
 *
 * @see org.jetlinks.core.message.DeviceMessage
 * @see org.jetlinks.core.message.DeviceMessageReply
 */
public class DownstreamCommand<T extends DeviceMessage, R extends DeviceMessageReply>
        extends AbstractCommand<Flux<R>, DownstreamCommand<T, R>> {

    @SuppressWarnings("all")
    public T getMessage() {
        Object msg = readable().get("message");
        if (msg instanceof DeviceMessage) {
            return (T) msg;
        }
        if (msg instanceof Map) {
            return convertMessage((Map<String, Object>) msg);
        }
        return convertMessage(readable());
    }

    @SuppressWarnings("all")
    protected T convertMessage(Map<String, Object> message) {
        return (T) MessageType
            .convertMessage(message)
            .orElse(null);
    }

    public DownstreamCommand<T, R> withMessage(Map<String, Object> message) {
        writable().put("message", message);
        return castSelf();
    }

    public DownstreamCommand<T, R> withMessage(DeviceMessage message) {
        writable().put("message", message);
        return castSelf();
    }

    public static ObjectType getCommonHeadersMetadata() {
        return new ObjectType()
                .addProperty("deviceId", "设备id", StringType.GLOBAL)
                .addProperty("headers", "消息头", new ObjectType()
                        .addProperty("timeout", "指定发送消息的超时时间", LongType.GLOBAL)
                        .addProperty("async", "是否异步", BooleanType.GLOBAL));
    }

}
