package org.jetlinks.sdk.server.device.cmd;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.message.MessageType;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.Optional;

/**
 * 发送消息给设备
 *
 * @see org.jetlinks.core.message.DeviceMessage
 */
@AllArgsConstructor
@NoArgsConstructor
public class DownstreamCommand extends AbstractCommand<Flux<DeviceMessage>, DownstreamCommand> {

    private String commandId;

    @SuppressWarnings("all")
    public DeviceMessage getMessage() {
        Object msg = readable().get("message");
        if (msg instanceof DeviceMessage) {
            return (DeviceMessage) msg;
        }
        if (msg instanceof Map) {
            return convertMessage((Map<String, Object>) msg);
        }
        return convertMessage(readable());
    }

    protected DeviceMessage convertMessage(Map<String, Object> message) {
        return (DeviceMessage) MessageType
            .convertMessage(message)
            .orElse(null);
    }

    public DownstreamCommand withMessage(Map<String, Object> message) {
        writable().put("message", message);
        return castSelf();
    }

    public DownstreamCommand withMessage(DeviceMessage message) {
        writable().put("message", message);
        return castSelf();
    }

    @Override
    public String getCommandId() {
        if (StringUtils.hasText(this.commandId)) {
            return this.commandId;
        }
        Optional<MessageType> message = MessageType
                .of(((Map) readable().get("message")));
        return message
                .map(Enum::name)
                .orElseGet(super::getCommandId);
    }
}
