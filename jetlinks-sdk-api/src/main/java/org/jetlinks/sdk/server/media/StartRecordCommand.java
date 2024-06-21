package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.utils.CastUtils;
import reactor.core.publisher.Mono;

@Schema(title = "开始录像命令")
public class StartRecordCommand extends AbstractCommand<Mono<MediaRecord>, StartRecordCommand> {

    @Schema(description = "录像名称")
    public String getName() {
        return getOrNull("name", String.class);
    }

    public StartRecordCommand setName(String name) {
        return with("name", name);
    }

    @Schema(description = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public StartRecordCommand setDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    @Schema(description = "通道ID")
    public String getChannelId() {
        return getOrNull("channelId", String.class);
    }

    public StartRecordCommand setChannelId(String channelId) {
        return with("channelId", channelId);
    }

    @Schema(description = "录像计划结束时间")
    public Long getPlanEndTime() {
        return getOrNull("planEndTime", Long.class);
    }

    public StartRecordCommand setPlanEndTime(Long planEndTime) {
        return with("planEndTime", planEndTime);
    }

    @Schema(description = "视频起始时间,录制历史视频时使用.")
    public Long getStreamStartTime() {
        return getOrNull("streamStartTime", Long.class);
    }

    public StartRecordCommand setStreamStartTime(Long streamStartTime) {
        return with("streamStartTime", streamStartTime);
    }

    @Schema(description = "视频截止时间,录制历史视频时使用.")
    public Long getStreamEndTime() {
        return getOrNull("streamEndTime", Long.class);
    }

    public StartRecordCommand setStreamEndTime(Long streamEndTime) {
        return with("streamEndTime", streamEndTime);
    }

    public MediaInfo getMedia() {
        return getOrNull("media", MediaInfo.class);
    }

    public StartRecordCommand setMedia(MediaInfo media) {
        return with("media", media);
    }

    @Schema(description = "是否进行摄像头本地的录像")
    public boolean isLocal() {
        return CastUtils.castBoolean(readable().get("local"));
    }

    public StartRecordCommand setLocal(boolean local) {
        writable().put("local", local);
        return this;
    }

    @Schema(description = "摄像头不支持本地录像时使用云端录像")
    public boolean isFallbackServer() {
        return CastUtils.castBoolean(readable().get("fallbackServer"));
    }

    public StartRecordCommand setFallbackServer(boolean fallbackServer) {
        writable().put("fallbackServer", fallbackServer);
        return this;
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(StartRecordCommand.class);
    }

}
