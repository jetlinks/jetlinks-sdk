package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.device.DeviceOperator;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.message.MessageType;
import org.jetlinks.core.message.function.FunctionInvokeMessage;
import org.jetlinks.core.message.function.FunctionInvokeMessageReply;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import reactor.core.publisher.Flux;

import java.util.Collections;
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
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(FunctionInvokeCommand.class));
        metadata.setName("调用设备功能");
        metadata.setDescription("向设备发送调用设备功能消息");

        SimplePropertyMetadata simplePropertyMetadata = SimplePropertyMetadata
            .of("message", "消息",
                getCommonHeadersMetadata()
                    .addProperty("functionId", "功能id", StringType.GLOBAL)
                    .addProperty("inputs", "参数",
                                 new ArrayType()
                                     .elementType(new ObjectType()
                                                      .addProperty("name", "参数名称", new StringType())
                                                      .addProperty("value", "参数值", new StringType()))));

        metadata.setInputs(Collections.singletonList(simplePropertyMetadata));
        return metadata;
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

}
