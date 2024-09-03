package org.jetlinks.sdk.server.device.cmd;

import org.hswebframework.web.exception.BusinessException;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.message.property.WritePropertyMessage;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.function.Function;

public class WritePropertyCommand extends DownstreamCommand {


    public static FunctionMetadata metadata() {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(WritePropertyCommand.class));
        metadata.setName("设置设备属性");
        metadata.setDescription("设置设备属性");

        SimplePropertyMetadata simplePropertyMetadata = SimplePropertyMetadata
                .of("message", "消息",
                    new ObjectType()
                            .addProperty("deviceId", "设备id", StringType.GLOBAL)
                            .addProperty("messageType", "消息类型", StringType.GLOBAL)
                            .addProperty("properties", " 要修改的属性", new ObjectType()));

        metadata.setInputs(Collections.singletonList(simplePropertyMetadata));
        return metadata;
    }


    public static CommandHandler<WritePropertyCommand, Flux<DeviceMessage>> createHandler(Function<WritePropertyCommand, Flux<DeviceMessage>> handler) {

        return CommandHandler
                .of(
                        WritePropertyCommand::metadata,
                        (cmd, ignore) -> handler.apply(cmd),
                        WritePropertyCommand::new
                );
    }

    public Mono<WritePropertyMessage> getWritePropertyMessage() {
        return Mono
                .justOrEmpty(getMessage())
                .cast(WritePropertyMessage.class)
                .onErrorMap(error -> new BusinessException("error.unsupported_property_format", error));
    }

}
