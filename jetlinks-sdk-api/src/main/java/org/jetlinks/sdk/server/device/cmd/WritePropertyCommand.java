package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.device.DeviceOperator;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.message.MessageType;
import org.jetlinks.core.message.property.WritePropertyMessage;
import org.jetlinks.core.message.property.WritePropertyMessageReply;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.ui.field.annotation.field.ui.DeviceDownStreamComponent;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.function.Function;


/**
 * 发送修改设备属性消息给设备
 *
 * @author zhouhao
 * @see WritePropertyCommand
 * @see DeviceOperator#messageSender()
 * @since 1.0.1
 */
@Schema(title = "设置设备属性", description = "设置设备属性")
@DeviceDownStreamComponent(type = MessageType.WRITE_PROPERTY)
public class WritePropertyCommand extends DownstreamCommand<WritePropertyMessage, WritePropertyMessageReply> {

    @Override
    public WritePropertyCommand withMessage(Map<String, Object> message) {
        super.withMessage(message);
        return this;
    }

    @Override
    public WritePropertyCommand withMessage(DeviceMessage message) {
        super.withMessage(message);
        return this;
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(WritePropertyCommand.class);
    }


    public static CommandHandler<WritePropertyCommand, Flux<WritePropertyMessageReply>> createHandler(
        Function<WritePropertyCommand, Flux<WritePropertyMessageReply>> handler) {

        return CommandHandler
            .of(
                WritePropertyCommand::metadata,
                (cmd, ignore) -> handler.apply(cmd),
                WritePropertyCommand::new
            );
    }

    @Override
    protected WritePropertyMessage convertMessage(Map<String, Object> message) {
        return MessageType.WRITE_PROPERTY.convert(message);
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
        private Properties properties;
    }
}
