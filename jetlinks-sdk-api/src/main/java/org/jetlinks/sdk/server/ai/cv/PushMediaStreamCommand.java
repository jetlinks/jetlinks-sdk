package org.jetlinks.sdk.server.ai.cv;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Mono;

/**
 * 推送任务视频流到指定的rtsp/rtmp地址
 *
 * @author zhouhao
 * @since 2.2
 * @see GetTaskMediaStreamCommand
 */
@Schema(title = "推送任务视频流")
public class PushMediaStreamCommand extends AbstractCommand<Mono<Void>, PushMediaStreamCommand> {

    @Schema(title = "任务ID")
    private String getTaskId() {
        return getOrNull("taskId", String.class);
    }

    public PushMediaStreamCommand setTaskId(String taskId) {
        with("taskId", taskId);
        return this;
    }

    @Schema(title = "RTSP推流地址")
    public String getRtsp() {
        return getOrNull("rtsp", String.class);
    }

    public PushMediaStreamCommand setRtsp(String rtsp) {
        with("rtsp", rtsp);
        return this;
    }

    @Schema(title = "RTMP推流地址")
    public String getRtmp() {
        return getOrNull("rtmp", String.class);
    }

    public PushMediaStreamCommand setRtmp(String rtmp) {
        with("rtmp", rtmp);
        return this;
    }


}
