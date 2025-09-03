package org.jetlinks.sdk.server.ai.llm.agent;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.sdk.server.ai.llm.LlmMessage;

@Getter
@Setter
public class ChatRequest<Self extends ChatRequest<Self>> extends LlmMessage<Self> {


    @Override
    public String getType() {
        return super.getType();
    }
}
