package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.annotation.DeviceSelector;
import org.jetlinks.sdk.server.commons.cmd.OperationByIdCommand;
import org.jetlinks.sdk.server.device.DeviceProperty;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Function;

/**
 * 查询设备最新的属性值.
 *
 * @author zhangji 2024/1/16
 */
@Schema(title = "根据ID查询设备最新的属性值")
public class QueryPropertyLatestCommand extends OperationByIdCommand<Flux<DeviceProperty>, QueryPropertyLatestCommand> {

    private static final long serialVersionUID = 1L;

    @Override
    @DeviceSelector(multiple = true)
    public List<Object> getIdList() {
        return super.getIdList();
    }

    public static CommandHandler<QueryPropertyLatestCommand, Flux<DeviceProperty>> createHandler(
        Function<QueryPropertyLatestCommand, Flux<DeviceProperty>> handler
    ) {
        return CommandHandler.of(
            QueryPropertyLatestCommand::metadata,
            (cmd, ignore) -> handler.apply(cmd),
            QueryPropertyLatestCommand::new
        );
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(QueryPropertyLatestCommand.class);
    }

}
