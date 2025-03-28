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
 * @since 2.2
 */
@Schema(title = "获取设备推流地址信息")
public class GetPushStreamCommand extends AbstractCommand<Mono<MediaStreamPushInfo>, GetPushStreamCommand> {

    @Schema(title = "推流服务ID",description = "为空时随机选择")
    public String getServerId() {
        return getOrNull("serverId", String.class);
    }

    public GetPushStreamCommand setServerId(String serverId) {
        return with("serverId", serverId);
    }

    @Schema(title = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public GetPushStreamCommand setDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    @Schema(title = "通道ID")
    public String getChannel() {
        return getOrNull("channel", String.class);
    }

    public GetPushStreamCommand setChannel(String channel) {
        return with("channel", channel);
    }

    @Schema(title = "开启RTP推流")
    public boolean isEnableRtp() {
        return CastUtils.castBoolean(getOrNull("enableRtp", Boolean.class));
    }

    public GetPushStreamCommand setEnableRtp(boolean enableRtp) {
        return with("enableRtp", enableRtp);
    }

    @Schema(title = "RTP推流时的SSRC值")
    public String getSsrc() {
        return getOrNull("ssrc", String.class);
    }

    public GetPushStreamCommand setSsrc(String ssrc) {
        return with("ssrc", ssrc);
    }

    @Schema(title = "是否为本地推流",description = "通过内网进行推流时设置为true")
    public boolean isLocalPusher() {
        return CastUtils.castBoolean(getOrNull("localPusher", Boolean.class));
    }

    public GetPushStreamCommand setLocalPusher(boolean localPusher) {
        return with("localPusher", localPusher);
    }

    @Schema(title = "是否为本地播放",description = "通过内网进行播放时设置为true")
    public boolean isLocalPlayer() {
        return CastUtils.castBoolean(getOrNull("localPlayer", Boolean.class));
    }

    public GetPushStreamCommand setLocalPlayer(boolean localPlayer) {
        return with("localPlayer", localPlayer);
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(ResolvableType.forType(GetPushStreamCommand.class));
    }
}
