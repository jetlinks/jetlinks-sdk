package org.jetlinks.sdk.server.ai.llm.agent.chat;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.sdk.server.ai.llm.agent.ChatResponse;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class AiGenerationChatResponse extends ChatResponse<AiGenerationChatResponse> {

    private boolean success;

    private String errorCode;

    private String errorMessage;

    private String content;

    private Integer hit;

    private Map<String, Object> data;

    private List<AiGenerationChatRequest.FileInfo> files;

    public AiGenerationChatResponse error(String errorCode, String errorMessage) {
        this.setSuccess(false);
        this.setErrorCode(errorCode);
        this.setErrorMessage(errorMessage);
        return this;
    }
}
