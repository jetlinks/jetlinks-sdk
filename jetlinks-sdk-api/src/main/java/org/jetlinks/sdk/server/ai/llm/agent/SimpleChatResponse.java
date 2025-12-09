package org.jetlinks.sdk.server.ai.llm.agent;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SimpleChatResponse {

    private boolean success;

    private String errorCode;

    private String errorMessage;

    private String content;

    private Map<String, Object> data;

    private List<OnceChatCommand.FileInfo> files;

    public static SimpleChatResponse error(String errorCode,String errorMessage) {
        SimpleChatResponse response = new SimpleChatResponse();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        return response;
    }
}
