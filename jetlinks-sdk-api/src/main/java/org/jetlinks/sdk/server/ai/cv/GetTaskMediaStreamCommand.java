package org.jetlinks.sdk.server.ai.cv;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.media.MediaInfo;
import reactor.core.publisher.Mono;

@Schema(title = "获取任务视频流信息")
public class GetTaskMediaStreamCommand extends AbstractCommand<Mono<MediaInfo>, GetTaskMediaStreamCommand> {

    @Schema(title = "任务ID")
    @NotBlank
    public String getTaskId() {
        return getOrNull("taskId", String.class);
    }

    @Schema(title = "视频源ID")
    @NotBlank
    public String getSourceId() {
        return getOrNull("sourceId", String.class);
    }

    public GetTaskMediaStreamCommand withTaskId(String taskId) {
        return with("taskId", taskId);
    }

    public GetTaskMediaStreamCommand withSourceId(String sourceId) {
        return with("sourceId", sourceId);
    }

}
