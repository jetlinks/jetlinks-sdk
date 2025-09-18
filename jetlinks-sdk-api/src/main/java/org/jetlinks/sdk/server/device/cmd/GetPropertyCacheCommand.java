package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.device.CachedDeviceProperty;
import org.jetlinks.sdk.server.ui.field.annotation.field.DateTime;
import org.jetlinks.sdk.server.ui.field.annotation.field.select.DevicePropertySelector;
import org.jetlinks.sdk.server.ui.field.annotation.field.select.DeviceSelector;
import reactor.core.publisher.Flux;

import java.util.List;

@Schema(title = "获取设备属性缓存")
public class GetPropertyCacheCommand extends AbstractCommand<Flux<CachedDeviceProperty>, GetPropertyCacheCommand> {

    @Schema(title = "设备ID")
    @DeviceSelector
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    @Schema(title = "属性ID")
    @DevicePropertySelector(multiple = true)
    public List<String> getProperties() {
        return getOrNull("properties", List.class);
    }

    @Schema(title = "基准时间",description = "获取离基准时间前最近的缓存.")
    @DateTime(timestamp = true)
    public Long getTimestamp() {
        return getOrNull("timestamp", Long.class);
    }

    public GetPropertyCacheCommand withTimestamp(long timestamp) {
        return with("timestamp", timestamp);
    }
    public GetPropertyCacheCommand withDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    public GetPropertyCacheCommand withProperties(List<String> properties) {
        return with("properties", properties);
    }
}
