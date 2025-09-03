package org.jetlinks.sdk.server.ai.llm.agent;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Flux;

@Schema(title = "对话命令")
public class ChatCommand extends AbstractCommand<Flux<ChatResponse<?>>, ChatCommand> {

    public ChatRequest<?> asRequest() {
        return as(ChatRequest.class);
    }

}
