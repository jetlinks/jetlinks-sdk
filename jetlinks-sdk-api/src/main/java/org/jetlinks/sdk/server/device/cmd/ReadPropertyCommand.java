package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.message.property.ReadPropertyMessage;
import org.jetlinks.core.message.property.ReadPropertyMessageReply;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.function.Function;

public class ReadPropertyCommand extends DownstreamCommand<ReadPropertyMessage, ReadPropertyMessageReply> {

    public static FunctionMetadata metadata() {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(ReadPropertyCommand.class));
        metadata.setName("获取设备属性");
        metadata.setDescription("获取设备属性");

        SimplePropertyMetadata simplePropertyMetadata = SimplePropertyMetadata
                .of("message", "消息",
                    new ObjectType()
                            .addProperty("deviceId", "设备id", StringType.GLOBAL)
                            .addProperty("messageType", "消息类型", StringType.GLOBAL)
                            .addProperty("properties", "要读取的属性列表",
                                         new ArrayType()
                                                 .elementType(new StringType())));

        metadata.setInputs(Collections.singletonList(simplePropertyMetadata));
        return metadata;
    }

    public static CommandHandler<ReadPropertyCommand, Flux<ReadPropertyMessageReply>> createHandler(Function<ReadPropertyCommand, Flux<ReadPropertyMessageReply>> handler) {

        return CommandHandler
                .of(
                        ReadPropertyCommand::metadata,
                        (cmd, ignore) -> handler.apply(cmd),
                        ReadPropertyCommand::new
                );
    }
}
