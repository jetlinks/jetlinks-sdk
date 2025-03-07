package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.commons.cmd.UnboundedResponseCommand;
import org.jetlinks.sdk.server.device.DeviceProperty;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;

import java.util.List;

@Schema(title = "订阅设备属性数据", description = "根据参数返回指定条数历史数据和实时数据")
public class SubscribeDevicePropertyCommand extends AbstractCommand<Flux<DeviceProperty>, SubscribeDevicePropertyCommand>
    implements UnboundedResponseCommand<DeviceProperty> {

    public static final String DEVICE_IDS = "deviceIds";
    public static final String PRODUCT_ID = "productId";
    public static final String HISTORY = "history";

    @Schema(title = "设备ID集合")
    public List<String> getDeviceIds() {
        return ConverterUtils
            .convertToList(readable().get(DEVICE_IDS), String::valueOf);
    }

    public SubscribeDevicePropertyCommand setDeviceIds(List<String> deviceIds) {
        return with(DEVICE_IDS, deviceIds);
    }

    @Schema(title = "历史数据条数")
    public Integer getHistory() {
        return (Integer) readable()
            .getOrDefault(HISTORY, 0);
    }

    public SubscribeDevicePropertyCommand setHistory() {
        return with(HISTORY, Integer.class);
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver
            .resolve(SubscribeDevicePropertyCommand.class);
    }

    @Schema(title = "产品ID")
    public String getProductId() {
        return String.valueOf(readable().get(PRODUCT_ID));
    }

    public SubscribeDevicePropertyCommand setProductId(String productId) {
        return with(PRODUCT_ID, productId);
    }
}
