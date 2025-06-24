package org.jetlinks.sdk.server.ai.llm.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Flux;

@Schema(title = "AI对话命令", description = "发起AI对话,获取对话结果")
public class ChatCommand extends AbstractCommand<Flux<ChatResponse>, ChatCommand> {

    @Schema(title = "对话ID")
    public String getChatId() {
        return getOrNull("chatId", String.class);
    }

    public ChatCommand setChatId(String chatId) {
        return with("chatId", chatId);
    }

    @Schema(title = "对话消息")
    public ChatMessage getMessage() {
        return getOrNull("message", ChatMessage.class);
    }

    public ChatCommand setMessage(ChatMessage message) {
        return with("message", message);
    }

    @Schema(title = "是否流式输出")
    public boolean isStream(){
        return Boolean.TRUE.equals(getOrNull("stream", Boolean.class));
    }

    public ChatCommand setStream(boolean stream) {
        return with("stream", stream);
    }

    @Schema(title = "用户标识")
    public String getUser() {
        return getOrNull("user", String.class);
    }

    public ChatCommand setUser(String user) {
        return with("user", user);
    }
}
