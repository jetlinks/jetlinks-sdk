package org.jetlinks.sdk.server.ai.llm.agent;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.sdk.server.ai.llm.LlmMessage;

@Getter
@Setter
public class ChatResponse<Self extends ChatResponse<Self>> extends LlmMessage<Self> {

}
