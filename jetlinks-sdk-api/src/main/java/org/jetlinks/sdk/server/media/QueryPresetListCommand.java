package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.commons.cmd.QueryListCommand;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;

/**
 * 查询预置位.
 *
 * @author zhangji 2024/6/16
 */
public class QueryPresetListCommand extends AbstractCommand<Flux<MediaPreset>,QueryPresetListCommand> {

    @Schema(description = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public QueryPresetListCommand setDeviceId(String deviceId) {
        writable().put("deviceId", deviceId);
        return this;
    }

    @Schema(description = "通道ID")
    public String getChannelId() {
        return getOrNull("channelId", String.class);
    }

    public QueryPresetListCommand setChannelId(String channelId) {
        writable().put("channelId", channelId);
        return this;
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(ResolvableType.forType(QueryPresetListCommand.class));
    }

}
