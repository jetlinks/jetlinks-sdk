package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.utils.CastUtils;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Mono;

/**
 * 获取设备推流地址,获取设备推流地址,可以用于推流到流媒体服务器.
 *
 * @author zhouhao
 * @see MediaStreamPushInfo#getRtsp()
 * @see MediaStreamPushInfo#getRtmp()
 * @see MediaStreamPushInfo#getRtp()
 * @since 1.0.1
 */
@Schema(title = "获取设备拉流地址信息")
public class GetPullStreamCommand extends AbstractCommand<Mono<MediaInfo>, GetPullStreamCommand> {

    @Schema(title = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public GetPullStreamCommand setDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    @Schema(title = "通道ID")
    public String getChannel() {
        return getOrNull("channel", String.class);
    }

    public GetPullStreamCommand setChannel(String channel) {
        return with("channel", channel);
    }

    @Schema(title = "是否为本地播放",description = "通过内网进行播放时设置为true")
    public boolean isLocalPlayer() {
        return CastUtils.castBoolean(getOrNull("localPlayer", Boolean.class));
    }

    public GetPullStreamCommand setLocalPlayer(boolean localPlayer) {
        return with("localPlayer", localPlayer);
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(ResolvableType.forType(GetPullStreamCommand.class));
    }
}
