package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.commons.cmd.OperationByIdCommand;
import org.jetlinks.sdk.server.device.DeviceProperty;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.function.Function;

/**
 * 查询设备最新的属性值.
 *
 * @author zhangji 2024/1/16
 */
public class QueryPropertyLatestCommand extends OperationByIdCommand<Flux<DeviceProperty>, QueryPropertyLatestCommand> {

    private static final long serialVersionUID = 1L;

    public static CommandHandler<QueryPropertyLatestCommand, Flux<DeviceProperty>> createHandler(
        Function<QueryPropertyLatestCommand, Flux<DeviceProperty>> handler
    ) {
        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(QueryPropertyLatestCommand.class));
                metadata.setName("根据ID查询设备最新的属性值");
                metadata.setInputs(
                    Collections.singletonList(SimplePropertyMetadata.of("id", "Id", StringType.GLOBAL))
                );
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            QueryPropertyLatestCommand::new
        );
    }

}
