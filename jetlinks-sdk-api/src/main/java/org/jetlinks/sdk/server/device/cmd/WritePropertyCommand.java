package org.jetlinks.sdk.server.device.cmd;

import org.hswebframework.web.exception.BusinessException;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.message.property.WritePropertyMessage;
import org.jetlinks.core.message.property.WritePropertyMessageReply;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;

public class WritePropertyCommand extends DownstreamCommand<WritePropertyMessage, WritePropertyMessageReply> {


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


    public static CommandHandler<WritePropertyCommand, Flux<WritePropertyMessageReply>> createHandler(Function<WritePropertyCommand, Flux<WritePropertyMessageReply>> handler) {

        return CommandHandler
                .of(
                        WritePropertyCommand::metadata,
                        (cmd, ignore) -> handler.apply(cmd),
                        WritePropertyCommand::new
                );
    }


    @Override
    public WritePropertyMessage getMessage() {
        WritePropertyMessage message = super.getMessage();
        if (Objects.isNull(message)) {
            throw new BusinessException("error.unsupported_property_format");
        }
        return message;
    }
}
