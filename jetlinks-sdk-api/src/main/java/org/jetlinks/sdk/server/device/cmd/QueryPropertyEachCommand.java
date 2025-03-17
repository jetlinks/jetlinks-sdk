package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.commons.cmd.QueryCommand;
import org.jetlinks.sdk.server.device.DeviceProperty;
import org.jetlinks.sdk.server.ui.field.annotation.field.select.DeviceSelector;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Function;

/**
 * 按条件查询指定ID设备的指定属性，不指定属性则查询全部属性
 */
@Schema(title = "按条件查询指定ID设备的指定属性", description = "若未指定属性，则查询设备全部属性")
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
                .convertToList(readable().get("properties"),
                               String::valueOf);
    }

    public QueryPropertyEachCommand withProperties(List<String> properties) {
        writable().put("properties", properties);
        return this;
    }


    public static CommandHandler<QueryPropertyEachCommand, Flux<DeviceProperty>> createHandler(
            Function<QueryPropertyEachCommand, Flux<DeviceProperty>> handler
    ) {
        return CommandHandler.of(
            QueryPropertyEachCommand::metadata,
                (cmd, ignore) -> handler.apply(cmd),
                QueryPropertyEachCommand::new
        );
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(QueryPropertyEachCommand.class);
    }

    @Getter
    @Setter
    protected static class InputSpec extends QueryCommand.InputSpec {

        @DeviceSelector
        @Schema(title = "设备ID")
        private String deviceId;

        @Schema(title = "物模型属性ID")
        private List<String> properties;

    }


}
