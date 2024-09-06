package org.jetlinks.sdk.server.media.broadcast;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.media.MediaStreamInfo;
import org.jetlinks.sdk.server.utils.CastUtils;
import reactor.core.publisher.Mono;

@Schema(title = "准备语音广播")
public class PrepareBroadcastCommand extends AbstractCommand<Mono<MediaStreamInfo>, PrepareBroadcastCommand> {

    @Schema(title = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public PrepareBroadcastCommand setDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    @Schema(title = "通道ID")
    public String getChannelId() {
        return getOrNull("channelId", String.class);
    }

    public PrepareBroadcastCommand setChannelId(String channelId) {
        return with("channelId", channelId);
    }

    @Schema(title = "是否为本地推流")
    public boolean isLocalPusher() {
        return CastUtils.castBoolean(readable().get("localPusher"));
    }

    public PrepareBroadcastCommand setLocalPusher(boolean localPusher) {
        return with("localPusher", localPusher);
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(PrepareBroadcastCommand.class);
    }

}
