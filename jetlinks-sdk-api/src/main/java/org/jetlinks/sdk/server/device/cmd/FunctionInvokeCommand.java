package org.jetlinks.sdk.server.device.cmd;

import org.hswebframework.web.exception.BusinessException;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.message.MessageType;
import org.jetlinks.core.message.function.FunctionInvokeMessage;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

public class FunctionInvokeCommand extends DownstreamCommand {

    @Override
    protected DeviceMessage convertMessage(Map<String, Object> message) {
        return MessageType
            .<DeviceMessage>convertMessage(message)
            .orElse(null);
    }

    public static FunctionMetadata metadata(){
        SimpleFunctionMetadata metadata =  new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(FunctionInvokeCommand.class));
        metadata.setName("调用设备功能");
        metadata.setDescription("向设备发起功能调用消息");

        SimplePropertyMetadata simplePropertyMetadata = SimplePropertyMetadata
                .of("message", "消息",
                    new ObjectType()
                            .addProperty("deviceId", "设备id", StringType.GLOBAL)
                            .addProperty("messageType", "消息类型", StringType.GLOBAL)
                            .addProperty("functionId", "功能id", StringType.GLOBAL)
                            .addProperty("inputs", "参数",
                                         new ArrayType()
                                                 .elementType(new ObjectType()
                                                                      .addProperty("name", "参数名称", new StringType())
                                                                      .addProperty("value", "参数值", new StringType()))));
        metadata.setInputs(Collections.singletonList(simplePropertyMetadata));
        return metadata;
    }

    public static CommandHandler<FunctionInvokeCommand, Flux<DeviceMessage>> createHandler(Function<FunctionInvokeCommand, Flux<DeviceMessage>> handler) {

        return CommandHandler
                .of(
                        FunctionInvokeCommand::metadata,
                        (cmd, ignore) -> handler.apply(cmd),
                        FunctionInvokeCommand::new
                );
    }

    public Mono<FunctionInvokeMessage> getFunctionInvokeMessage() {
        return Mono
                .justOrEmpty(getMessage())
                .cast(FunctionInvokeMessage.class)
                .onErrorMap(error -> new BusinessException("error.unsupported_property_format", error));
    }
}
