package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.annotation.ui.Selector;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.commons.cmd.QueryCommand;
import org.jetlinks.sdk.server.device.DeviceProperty;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 按条件查询指定ID设备的指定属性，不指定属性则查询全部属性
 */
public class QueryPropertyEachCommand extends QueryCommand<Flux<DeviceProperty>, QueryPropertyEachCommand> {

    @Selector(type = "device")
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
                () -> {
                    Map<String, Object> selectorMap = new HashMap<>();
                    selectorMap.put("type", "device");
                    selectorMap.put("multiple", false);
                    SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                    metadata.setId(CommandUtils.getCommandIdByType(QueryPropertyEachCommand.class));
                    metadata.setName("按条件查询指定ID设备的指定属性");
                    metadata.setDescription("若未指定属性，则查询设备全部属性");
                    metadata.setInputs(
                        Arrays.asList(SimplePropertyMetadata.of("id", "Id", StringType.GLOBAL)
                                .expand("selector", selectorMap),
                                          SimplePropertyMetadata.of("properties",
                                                                    "物模型属性ID",
                                                                    new ArrayType()
                                                                            .elementType(StringType.GLOBAL)),
                                          getTermsMetadata())
                    );
                    return metadata;
                },
                (cmd, ignore) -> handler.apply(cmd),
                QueryPropertyEachCommand::new
        );
    }
}
