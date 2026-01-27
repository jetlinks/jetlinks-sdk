package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.utils.CastUtils;
import reactor.core.publisher.Mono;


/**
 * 获取流地址
 */
@Schema(title = "获取流地址信息")
public class GetMediaStreamCommand extends AbstractCommand<Mono<MediaInfo>, GetMediaStreamCommand> {

    @Schema(title = "app")
    public String getApp() {
        return getOrNull("app", String.class);
    }

    public GetMediaStreamCommand setApp(String app) {
        writable().put("app", app);
        return this;
    }

    @Schema(title = "流Id")
    public String getStreamId() {
        return getOrNull("streamId", String.class);
    }

    public GetMediaStreamCommand setStreamId(String streamId) {
        writable().put("streamId", streamId);
        return this;
    }

    @Schema(title = "是否为本地播放", description = "通过内网进行播放或代理播放时设置为true")
    public boolean isLocal() {
        return CastUtils.castBoolean(getOrNull("local", Boolean.class));
    }

    public GetMediaStreamCommand setLocalPlayer(boolean localPlayer) {
        return with("local", localPlayer);
    }
}
