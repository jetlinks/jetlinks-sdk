package org.jetlinks.sdk.server.media.broadcast;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.media.MediaStreamInfo;
import org.jetlinks.sdk.server.utils.CastUtils;
import reactor.core.publisher.Mono;

@Schema(title = "开始语音广播")
public class StartBroadcastCommand extends AbstractCommand<Mono<Void>, StartBroadcastCommand> {

    @Schema(title = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public StartBroadcastCommand setDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    @Schema(title = "通道ID")
    public String getChannelId() {
        return getOrNull("channelId", String.class);
    }

    public StartBroadcastCommand setChannelId(String channelId) {
        return with("channelId", channelId);
    }

    public MediaStreamInfo getStreamInfo() {
        return getOrNull("stream", MediaStreamInfo.class);
    }

    public StartBroadcastCommand setStreamInfo(MediaStreamInfo streamInfo) {
        return with("stream", streamInfo);
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(StartBroadcastCommand.class);
    }

}
