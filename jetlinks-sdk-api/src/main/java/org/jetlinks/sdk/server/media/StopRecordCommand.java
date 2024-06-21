package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.utils.CastUtils;
import reactor.core.publisher.Mono;

@Schema(title = "停止录像命令")
public class StopRecordCommand extends AbstractCommand<Mono<MediaRecord>, StopRecordCommand> {

    @Schema(description = "录像ID")
    public String getId() {
        return getOrNull("id", String.class);
    }

    public StopRecordCommand setId(String id) {
        return with("id", id);
    }

    @Schema(description = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public StopRecordCommand setDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    @Schema(description = "通道ID")
    public String getChannelId() {
        return getOrNull("channelId", String.class);
    }

    public StopRecordCommand setChannelId(String channelId) {
        return with("channelId", channelId);
    }

    @Schema(description = "视频流ID")
    public String getStreamId() {
        return getOrNull("streamId", String.class);
    }

    public StopRecordCommand setStreamId(String streamId) {
        return with("streamId", streamId);
    }

    @Schema(description = "是否进行摄像头本地的录像")
    public boolean isLocal() {
        return CastUtils.castBoolean(readable().get("local"));
    }

    public StopRecordCommand setLocal(boolean local) {
        writable().put("local", local);
        return this;
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(StopRecordCommand.class);
    }
}
