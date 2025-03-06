package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandConstant;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.device.DeviceProperty;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Function;

@Schema(description = "设备消息订阅命令，根据参数返回指定条数历史数据和实时数据")
public class DeviceMessageSubscriptionCommand extends AbstractCommand<Flux<DeviceProperty>, DeviceMessageSubscriptionCommand> {

    public static final String DEVICE_IDS = "deviceIds";
    public static final String PRODUCT_ID = "productId";
    public static final String HISTORY = "history";

    @Schema(description = "设备ids")
    public List<String> getDeviceIds() {
        return ConverterUtils
            .convertToList(readable().get(DEVICE_IDS), String::valueOf);
    }

    public DeviceMessageSubscriptionCommand setDeviceIds(List<String> deviceIds) {
        return with(DEVICE_IDS, deviceIds);
    }

    @Schema(description = "历史数据条数")
    public Integer getHistory() {
        return (Integer) readable()
            .getOrDefault(HISTORY, 0);
    }

    public DeviceMessageSubscriptionCommand setHistory() {
        return with(HISTORY, Integer.class);
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(DeviceMessageSubscriptionCommand.class)
            .expand(CommandConstant.UNBOUNDED, true);
    }

    @Schema(description = "产品id")
    public String getProductId() {
        return String.valueOf(readable().get(PRODUCT_ID));
    }

    public DeviceMessageSubscriptionCommand setProductId(String productId) {
        return with(PRODUCT_ID, productId);
    }


    public static CommandHandler<DeviceMessageSubscriptionCommand, Flux<DeviceProperty>> createHandler(
        Function<DeviceMessageSubscriptionCommand, Flux<DeviceProperty>> handler) {

        return CommandHandler
            .of(
                DeviceMessageSubscriptionCommand::metadata,
                (cmd, ignore) -> handler.apply(cmd),
                DeviceMessageSubscriptionCommand::new
            );
    }
}
