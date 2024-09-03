package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
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
import java.util.function.Function;

public class FunctionInvokeCommand extends DownstreamCommand<FunctionInvokeMessage, FunctionInvokeMessageReply> {

    public static FunctionMetadata metadata() {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(FunctionInvokeCommand.class));
        metadata.setName("调用设备功能");
        metadata.setDescription("向设备发起功能调用消息");

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

    public static CommandHandler<FunctionInvokeCommand, Flux<FunctionInvokeMessageReply>> createHandler(Function<FunctionInvokeCommand, Flux<FunctionInvokeMessageReply>> handler) {

        return CommandHandler
                .of(
                        FunctionInvokeCommand::metadata,
                        (cmd, ignore) -> handler.apply(cmd),
                        FunctionInvokeCommand::new
                );
    }

}
