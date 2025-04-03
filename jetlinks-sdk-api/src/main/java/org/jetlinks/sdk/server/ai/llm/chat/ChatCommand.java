package org.jetlinks.sdk.server.ai.llm.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Flux;

@Schema(title = "AI对话命令", description = "发起AI对话,获取对话结果")
public class ChatCommand extends AbstractCommand<Flux<ChatResponse>, ChatCommand> {

    @Schema(description = "对话ID")
    public String getChatId() {
        return getOrNull("chatId", String.class);
    }

    public ChatCommand setChatId(String chatId) {
        return with("chatId", chatId);
    }

    @Schema(description = "对话消息")
    public ChatMessage getMessage() {
        return getOrNull("message", ChatMessage.class);
    }

    public ChatCommand setMessage(ChatMessage message) {
        return with("message", message);
    }

}
