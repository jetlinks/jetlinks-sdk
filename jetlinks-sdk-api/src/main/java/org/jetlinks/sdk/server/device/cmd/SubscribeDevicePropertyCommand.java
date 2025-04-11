package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.commons.cmd.UnboundedResponseCommand;
import org.jetlinks.sdk.server.device.DeviceProperty;
import org.jetlinks.sdk.server.ui.field.annotation.field.select.DevicePropertySelector;
import org.jetlinks.sdk.server.ui.field.annotation.field.select.DeviceSelector;
import org.jetlinks.sdk.server.ui.field.annotation.field.select.ProductSelector;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Flux;

import java.util.List;

@Schema(title = "订阅设备属性数据", description = "根据参数设备的实时属性数据")
public class SubscribeDevicePropertyCommand extends AbstractCommand<Flux<DeviceProperty>, SubscribeDevicePropertyCommand>
    implements UnboundedResponseCommand<DeviceProperty> {

    public static final String DEVICE_IDS = "deviceIds";
    public static final String PRODUCT_ID = "productId";
    public static final String PROPERTY_ID = "propertyIds";

    @DeviceSelector(multiple = true)
    @Order(1)
    @Schema(title = "设备ID集合",description = "为空订阅所有设备")
    public List<String> getDeviceIds() {
        return ConverterUtils
            .convertToList(readable().get(DEVICE_IDS), String::valueOf);
    }

    public SubscribeDevicePropertyCommand setDeviceIds(List<String> deviceIds) {
        return with(DEVICE_IDS, deviceIds);
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver
            .resolve(SubscribeDevicePropertyCommand.class);
    }

    @ProductSelector
    @Schema(title = "产品ID")
    @Order(0)
    public String getProductId() {
        return getOrNull(PRODUCT_ID, String.class);
    }

    public SubscribeDevicePropertyCommand setProductId(String productId) {
        return with(PRODUCT_ID, productId);
    }


    @DevicePropertySelector(multiple = true, deviceIdKey = DEVICE_IDS, productIdKey = PRODUCT_ID)
    @Schema(title = "属性ID集合", description = "为空订阅所有属性")
    @Order(3)
    public List<String> getPropertyIds() {
        return ConverterUtils
            .convertToList(readable().get(PROPERTY_ID), String::valueOf);
    }

    public SubscribeDevicePropertyCommand setPropertyIds(List<String> propertyIds) {
        return with(PROPERTY_ID, propertyIds);
    }
}
