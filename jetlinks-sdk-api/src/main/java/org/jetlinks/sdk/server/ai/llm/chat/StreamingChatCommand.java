package org.jetlinks.sdk.server.ai.llm.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractStreamCommand;
import org.jetlinks.sdk.server.ai.llm.LlmCommand;

@Schema(title = "流式AI对话命令", description = "发起AI对话,获取对话结果")
public class StreamingChatCommand extends AbstractStreamCommand<ChatMessage, ChatResponse, StreamingChatCommand> implements LlmCommand {

    @Schema(title = "对话ID")
    public String getChatId() {
        return getOrNull("chatId", String.class);
    }

    public StreamingChatCommand setChatId(String chatId) {
        return with("chatId", chatId);
    }

    @Schema(title = "用户标识")
    public String getUser() {
        return getOrNull("user", String.class);
    }

    public StreamingChatCommand setUser(String user) {
        return with("user", user);
    }
}
