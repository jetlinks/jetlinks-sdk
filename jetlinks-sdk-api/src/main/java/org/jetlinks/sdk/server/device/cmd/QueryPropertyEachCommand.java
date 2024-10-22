package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.commons.cmd.QueryCommand;
import org.jetlinks.sdk.server.device.DeviceProperty;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 按条件查询指定ID设备的指定属性，不指定属性则查询全部属性
 */
public class QueryPropertyEachCommand extends QueryCommand<Flux<DeviceProperty>, QueryPropertyEachCommand> {

    public String getDeviceId() {
        return (String) readable().get("deviceId");
    }

    public QueryPropertyEachCommand withDeviceId(String deviceId) {
        writable().put("deviceId", deviceId);
        return this;
    }

    public List<String> getProperties() {
        return ConverterUtils
                .convertToList(readable().get("property"),
                               String::valueOf);
    }

    public QueryPropertyEachCommand withProperty(String property) {
        writable().put("property", property);
        return this;
    }


    public static CommandHandler<QueryPropertyEachCommand, Flux<DeviceProperty>> createHandler(
            Function<QueryPropertyEachCommand, Flux<DeviceProperty>> handler
    ) {
        return CommandHandler.of(
                () -> {
                    SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                    metadata.setId(CommandUtils.getCommandIdByType(QueryPropertyEachCommand.class));
                    metadata.setName("按条件查询指定ID设备的指定属性");
                    metadata.setInputs(
                            Collections.singletonList(SimplePropertyMetadata.of("id", "Id", StringType.GLOBAL))
                    );
                    return metadata;
                },
                (cmd, ignore) -> handler.apply(cmd),
                QueryPropertyEachCommand::new
        );
    }
}
