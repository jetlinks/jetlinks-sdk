package org.jetlinks.sdk.server.media;

import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.utils.CastUtils;
import reactor.core.publisher.Mono;

public class StartPushStreamingCommand extends AbstractCommand<Mono<MediaStreamInfo>, StartPushStreamingCommand> {

    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public StartPushStreamingCommand setDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    public String getChannelId() {
        return getOrNull("channelId", String.class);
    }

    public StartPushStreamingCommand setChannelId(String channelId) {
        return with("channelId", channelId);
    }

    public String getStreamId() {
        return getOrNull("streamId", String.class);
    }

    public StartPushStreamingCommand setStreamId(String streamId) {
        return with("streamId", streamId);
    }

    public String getDestination() {
        return getOrNull("destination", String.class);
    }

    public StartPushStreamingCommand setDestination(String destination) {
        return with("destination", destination);
    }

    public String getSdp() {
        return getOrNull("sdp", String.class);
    }

    public StartPushStreamingCommand setSdp(String sdp) {
        return with("sdp", sdp);
    }

    // 录像相关参数

    public boolean isForRecord() {
        return CastUtils.castBoolean(readable().get("forRecord"));
    }

    public StartPushStreamingCommand setForRecord(boolean forRecord) {
        return with("forRecord", forRecord);
    }

    public Long getStartWith() {
        return getOrNull("startWith", Long.class);
    }

    public StartPushStreamingCommand setStartWith(Long startWith) {
        return with("startWith", startWith);
    }

    public Long getEndWith() {
        return getOrNull("endWith", Long.class);
    }

    public StartPushStreamingCommand setEndWith(Long endWith) {
        return with("endWith", endWith);
    }

    public Integer getDownloadSpeed() {
        return getOrNull("downloadSpeed", Integer.class);
    }

    public StartPushStreamingCommand setDownloadSpeed(Integer downloadSpeed) {
        return with("downloadSpeed", downloadSpeed);
    }

}
