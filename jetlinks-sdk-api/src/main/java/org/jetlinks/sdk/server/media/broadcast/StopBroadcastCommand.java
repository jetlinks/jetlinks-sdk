package org.jetlinks.sdk.server.media.broadcast;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.media.MediaStreamInfo;
import reactor.core.publisher.Mono;

@Schema(title = "停止语音广播")
public class StopBroadcastCommand extends AbstractCommand<Mono<Void>, StopBroadcastCommand> {

    @Schema(title = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public StopBroadcastCommand setDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    @Schema(title = "通道ID")
    public String getChannelId() {
        return getOrNull("channelId", String.class);
    }

    public StopBroadcastCommand setChannelId(String channelId) {
        return with("channelId", channelId);
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(StopBroadcastCommand.class);
    }
}
