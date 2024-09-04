package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.message.MessageType;
import org.jetlinks.core.message.property.WritePropertyMessage;
import org.jetlinks.core.message.property.WritePropertyMessageReply;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ObjectType;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

public class WritePropertyCommand extends DownstreamCommand<WritePropertyMessage, WritePropertyMessageReply> {


    public static FunctionMetadata metadata() {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(WritePropertyCommand.class));
        metadata.setName("设置设备属性");
        metadata.setDescription("设置设备属性");

        SimplePropertyMetadata simplePropertyMetadata = SimplePropertyMetadata
                .of("message", "消息",
                    getCommonHeadersMetadata()
                            .addProperty("properties", "需要修改的属性", new ObjectType()));

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
    protected WritePropertyMessage convertMessage(Map<String, Object> message) {
        return MessageType.WRITE_PROPERTY.convert(message);
    }
}