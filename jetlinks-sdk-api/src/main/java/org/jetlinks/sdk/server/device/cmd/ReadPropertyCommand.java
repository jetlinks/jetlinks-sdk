package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.device.DeviceOperator;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.message.MessageType;
import org.jetlinks.core.message.property.ReadPropertyMessage;
import org.jetlinks.core.message.property.ReadPropertyMessageReply;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.ui.field.annotation.field.ui.DeviceDownStreamComponent;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.function.Function;

/**
 * 发送读取设备属性消息给设备
 *
 * @author zhouhao
 * @see ReadPropertyMessage
 * @see DeviceOperator#messageSender()
 * @since 1.0.1
 */
@Schema(title = "获取设备属性", description = "获取设备属性")
@DeviceDownStreamComponent(type = MessageType.READ_PROPERTY)
public class ReadPropertyCommand extends DownstreamCommand<ReadPropertyMessage, ReadPropertyMessageReply> {

    @Override
    public ReadPropertyCommand withMessage(Map<String, Object> message) {
        super.withMessage(message);
        return this;
    }

    @Override
    public ReadPropertyCommand withMessage(DeviceMessage message) {
        super.withMessage(message);
        return this;
    }

    @Override
    protected ReadPropertyMessage convertMessage(Map<String, Object> message) {
        return MessageType.READ_PROPERTY.convert(message);
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(ReadPropertyCommand.class);
    }

    public static CommandHandler<ReadPropertyCommand, Flux<ReadPropertyMessageReply>> createHandler(
        Function<ReadPropertyCommand, Flux<ReadPropertyMessageReply>> handler) {

        return CommandHandler
            .of(
                ReadPropertyCommand::metadata,
                (cmd, ignore) -> handler.apply(cmd),
                ReadPropertyCommand::new
            );
    }

    @Setter
    @Getter
    public static class InputSpec {

        @Schema(title = "消息")
        private Message message;
    }

    @Setter
    @Getter
    public static class Message extends DownstreamCommand.Message {

        @Schema(title = "需要修改的属性")
        private Properties[] properties;
    }
}
