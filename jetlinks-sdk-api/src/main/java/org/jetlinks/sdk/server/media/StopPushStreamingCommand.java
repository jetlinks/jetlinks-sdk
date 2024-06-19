package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.utils.CastUtils;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

@Schema(title = "停止推流")
public class StopPushStreamingCommand extends AbstractCommand<Mono<Void>, StopPushStreamingCommand> {

    @Schema(description = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public StopPushStreamingCommand setDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    @Schema(description = "通道ID")
    public String getChannelId() {
        return getOrNull("channelId", String.class);
    }

    public StopPushStreamingCommand setChannelId(String channelId) {
        return with("channelId", channelId);
    }

    @Schema(description = "流ID")
    public String getStreamId() {
        return getOrNull("streamId", String.class);
    }

    public StopPushStreamingCommand setStreamId(String streamId) {
        return with("streamId", streamId);
    }

    /**
     * @return 开始推流时返回的其他信息
     * @see MediaStreamInfo#getOthers()
     */
    @Schema(description = "开始推流时返回的其他信息")
    public Map<String, Object> getOthers() {
        @SuppressWarnings("all")
        Map<String, Object> others = getOrNull("others", Map.class);
        return others == null ? Collections.emptyMap() : others;
    }

    public StopPushStreamingCommand setOthers(Map<String, Object> others) {
        return with("others", others);
    }


    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(StopPushStreamingCommand.class);
    }

}
