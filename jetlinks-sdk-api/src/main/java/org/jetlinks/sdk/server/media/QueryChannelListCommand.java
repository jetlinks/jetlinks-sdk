package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.commons.cmd.QueryListCommand;
import org.springframework.core.ResolvableType;

/**
 * 输入描述.
 *
 * @author zhangji 2024/6/16
 */
public class QueryChannelListCommand extends QueryListCommand<MediaChannel> {

    @Schema(description = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public QueryChannelListCommand setDeviceId(String deviceId) {
        writable().put("deviceId", deviceId);
        return this;
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(ResolvableType.forType(QueryChannelListCommand.class));
    }

}
