package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.message.DeviceMessageReply;
import org.jetlinks.core.message.MessageType;
import org.jetlinks.sdk.server.ui.field.annotation.field.select.DeviceSelector;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送消息给设备
 *
 * @see DeviceMessage
 * @see DeviceMessageReply
 */
@Schema(title = "发送消息给设备")
public class DownstreamCommand<T extends DeviceMessage, R extends DeviceMessageReply>
        extends AbstractConvertCommand<Flux<R>, DownstreamCommand<T, R>> {

    @SuppressWarnings("all")
    public static <T extends DeviceMessage, R extends DeviceMessageReply> DownstreamCommand<T, R> of(Class<R> replyClass) {
        DownstreamCommand<T, R> downstreamCommand = new DownstreamCommand<>();
        return downstreamCommand.withConverter(r -> {
            if (replyClass.isInstance(r)) {
                return ((R) r);
            }
            return FastBeanCopier.copy(r, replyClass);
        });
    }

    @SuppressWarnings("all")
    public static <T extends DeviceMessage, R extends DeviceMessageReply> DownstreamCommand<T, R> of() {
        DownstreamCommand<T, R> downstreamCommand = new DownstreamCommand<>();
        return downstreamCommand.withConverter(r -> {
            if (r instanceof DeviceMessageReply) {
                return r;
            }
            if (r instanceof Map) {
                return downstreamCommand.convertMessage((Map<String, Object>) r);
            }
            return downstreamCommand.convertMessage(FastBeanCopier.copy(r, new HashMap<>()));
        });
    }

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


    @Setter
    @Getter
    protected static class InputSpec {

        @Schema(title = "消息")
        private Message message;
    }

    @Setter
    @Getter
    protected static class Headers {

        @Schema(title = "指定发送消息的超时时间")
        private Long timeout;

        @Schema(title = "是否异步")
        private Boolean async;
    }

    @Setter
    @Getter
    protected static class Message {

        @DeviceSelector
        @NotBlank
        @Schema(title = "设备id")
        private String deviceId;

        @Schema(title = "消息头")
        private Headers headers;
    }

    public static class Properties {

    }
}
