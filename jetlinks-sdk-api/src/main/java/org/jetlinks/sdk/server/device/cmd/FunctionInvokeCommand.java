package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.message.MessageType;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;

import java.util.Map;

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

        return metadata;
    }
}
