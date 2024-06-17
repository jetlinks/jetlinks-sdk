package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.commons.cmd.QueryListCommand;
import org.springframework.core.ResolvableType;

/**
 * 查询预置位.
 *
 * @author zhangji 2024/6/16
 */
public class QueryPresetListCommand extends QueryListCommand<MediaPreset> {

    @Schema(description = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public QueryPresetListCommand setDeviceId(String deviceId) {
        writable().put("deviceId", deviceId);
        return this;
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(ResolvableType.forType(QueryPresetListCommand.class));
    }

}
