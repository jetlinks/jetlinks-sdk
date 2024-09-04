package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.device.DeviceOperator;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.message.MessageType;
import org.jetlinks.core.message.property.ReadPropertyMessage;
import org.jetlinks.core.message.property.ReadPropertyMessageReply;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.StringType;
import reactor.core.publisher.Flux;

import java.util.Collections;
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
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(ReadPropertyCommand.class));
        metadata.setName("获取设备属性");
        metadata.setDescription("获取设备属性");

        SimplePropertyMetadata simplePropertyMetadata = SimplePropertyMetadata
            .of("message", "消息",
                getCommonHeadersMetadata()
                    .addProperty("properties", "需要读取的属性列表",
                                 new ArrayType()
                                     .elementType(new StringType())));


        metadata.setInputs(Collections.singletonList(simplePropertyMetadata));
        return metadata;
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
}
