package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Mono;

@Schema(description = "开始录像命令")
public class StartRecordCommand extends AbstractCommand<Mono<MediaRecord>, StartRecordCommand> {

    public String getName() {
        return getOrNull("name", String.class);
    }

    public StartRecordCommand setName(String name) {
        return with("name", name);
    }

    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public StartRecordCommand setDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    public String getChannelId() {
        return getOrNull("channelId", String.class);
    }

    public StartRecordCommand setChannelId(String channelId) {
        return with("channelId", channelId);
    }

    public Long getPlanEndTime() {
        return getOrNull("planEndTime", Long.class);
    }

    public StartRecordCommand setPlanEndTime(Long planEndTime) {
        return with("planEndTime", planEndTime);
    }

    public Long getStreamStartTime() {
        return getOrNull("streamStartTime", Long.class);
    }

    public StartRecordCommand setStreamStartTime(Long streamStartTime) {
        return with("streamStartTime", streamStartTime);
    }

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

}
