package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.message.MessageType;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.MetadataParser;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.EnumType;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class UpstreamCommand extends AbstractCommand<Mono<Void>, UpstreamCommand> {

    public UpstreamCommand() {

    }

    public UpstreamCommand(DeviceMessage message) {
        withMessage(message);
    }

    @SuppressWarnings("all")
    public DeviceMessage getMessage() {
        Object msg = readable().get("message");
        if (msg instanceof DeviceMessage) {
            return (DeviceMessage) msg;
        }
        if (msg instanceof Map) {
            return (DeviceMessage) MessageType
                .convertMessage(((Map) msg))
                .orElse(null);
        }
        return null;
    }

    public UpstreamCommand withMessage(Map<String, Object> message) {
        writable().put("message", message);
        return castSelf();
    }

    public UpstreamCommand withMessage(DeviceMessage message) {
        writable().put("message", message);
        return castSelf();
    }

    public static FunctionMetadata metadata() {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId("Upstream");
        metadata.setName("上报设备消息");

        metadata.setInputs(
            Collections.singletonList(
                SimplePropertyMetadata.of("message", "消息内容", new ObjectType()
                    .addProperty("deviceId", "设备ID", StringType.GLOBAL)
                    .addProperty("messageId", "消息ID", StringType.GLOBAL)
                    .addProperty("messageType", new EnumType()
                        .addElement(EnumType.Element.of(MessageType.READ_PROPERTY.name(), "属性上报"))
                        .addElement(EnumType.Element.of(MessageType.EVENT.name(), "事件上报"))
                        .addElement(EnumType.Element.of(MessageType.READ_PROPERTY_REPLY.name(), "属性读取回复"))
                        .addElement(EnumType.Element.of(MessageType.WRITE_PROPERTY_REPLY.name(), "属性写入回复"))
                        .addElement(EnumType.Element.of(MessageType.INVOKE_FUNCTION_REPLY.name(), "功能调用回复"))
                    )
                    .addProperty("properties", "属性信息", new ObjectType())
                    .addProperty("event", "事件ID", StringType.GLOBAL)
                    .addProperty("data", "事件数据", new ObjectType())
                    .addProperty("functionId", "功能ID", StringType.GLOBAL)
                    .addProperty("output", "功能输出结果", new ObjectType())
                )));

        return metadata;
    }

}
