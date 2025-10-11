package org.jetlinks.sdk.server.ai.llm.agent;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Schema(title = "任务命令")
public class TaskCommand extends AbstractCommand<Flux<TaskResponse>, TaskCommand> {


    @Schema(title = "是否异步执行")
    public boolean getAsync() {
        return Optional.ofNullable(getOrNull("async", Boolean.class)).orElse(true);
    }

    public TaskCommand setAsync(boolean async) {
        return with("async", async);
    }

    public ChatRequest<?> asRequest() {
        return as(ChatRequest.class);
    }

}
