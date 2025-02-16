package org.jetlinks.sdk.server.ai.llm;

import org.jetlinks.core.command.AbstractStreamCommand;
import org.jetlinks.core.command.StreamCommand;
import org.jetlinks.sdk.server.ai.AiCommand;
import org.jetlinks.sdk.server.ai.llm.chat.ChatMessage;

public class ChatCommand extends AbstractStreamCommand<ChatMessage, ChatOutput, ChatCommand>
    implements AiCommand<ChatOutput>,
    StreamCommand<ChatMessage, ChatOutput> {




}
