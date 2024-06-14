package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Mono;

@Schema(description = "停止录像命令")
public class StopRecordCommand extends AbstractCommand<Mono<MediaRecord>, StopRecordCommand> {

    public String getId() {
        return getOrNull("id", String.class);
    }

    public StopRecordCommand setId(String id) {
        return with("id", id);
    }

    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public StopRecordCommand setDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    public String getChannelId() {
        return getOrNull("channelId", String.class);
    }

    public StopRecordCommand setChannelId(String channelId) {
        return with("channelId", channelId);
    }

    public String getStreamId() {
        return getOrNull("streamId", String.class);
    }

    public StopRecordCommand setStreamId(String streamId) {
        return with("streamId", streamId);
    }

}
