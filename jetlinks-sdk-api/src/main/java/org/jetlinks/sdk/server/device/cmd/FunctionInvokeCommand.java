package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.device.DeviceOperator;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.message.MessageType;
import org.jetlinks.core.message.function.FunctionInvokeMessage;
import org.jetlinks.core.message.function.FunctionInvokeMessageReply;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.ui.field.annotation.field.select.FunctionSelector;
import org.jetlinks.sdk.server.ui.field.annotation.field.ui.DeviceDownStreamComponent;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.function.Function;

/**
 * 向设备发送调用设备功能消息
 *
 * @author zhouhao
 * @see org.jetlinks.core.message.function.FunctionInvokeMessage
 * @see DeviceOperator#messageSender()
 * @since 1.0.1
 */
@Schema(title = "调用设备功能", description = "向设备发送调用设备功能消息")
@DeviceDownStreamComponent(type = MessageType.INVOKE_FUNCTION)
public class FunctionInvokeCommand extends DownstreamCommand<FunctionInvokeMessage, FunctionInvokeMessageReply> {

    @Override
    public FunctionInvokeCommand withMessage(Map<String, Object> message) {
        super.withMessage(message);
        return this;
    }

    @Override
    public FunctionInvokeCommand withMessage(DeviceMessage message) {
        super.withMessage(message);
        return this;
    }

    @Override
    protected FunctionInvokeMessage convertMessage(Map<String, Object> message) {
        return MessageType.INVOKE_FUNCTION.convert(message);
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(FunctionInvokeCommand.class);
    }

    public static CommandHandler<FunctionInvokeCommand, Flux<FunctionInvokeMessageReply>> createHandler(
        Function<FunctionInvokeCommand, Flux<FunctionInvokeMessageReply>> handler) {

        return CommandHandler
            .of(
                FunctionInvokeCommand::metadata,
                (cmd, ignore) -> handler.apply(cmd),
                FunctionInvokeCommand::new
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

        @FunctionSelector(deviceIdKey = "deviceId")
        @NotBlank
        @Schema(title = "功能id")
        @Order(1)
        private String functionId;

        @Schema(title = "参数")
        private Inputs[] inputs;
    }

    @Setter
    @Getter
    public static class Inputs {
        @Schema(title = "参数名称")
        private String name;

        @Schema(title = "参数值")
        private String value;
    }

}
