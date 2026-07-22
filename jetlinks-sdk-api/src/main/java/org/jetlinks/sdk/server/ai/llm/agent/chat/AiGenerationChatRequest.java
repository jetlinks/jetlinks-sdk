package org.jetlinks.sdk.server.ai.llm.agent.chat;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.sdk.server.ai.llm.agent.ChatRequest;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class AiGenerationChatRequest extends ChatRequest<AiGenerationChatRequest> {
    private String content;
    private List<FileInfo> files;
    private Map<String, Object> params;
    private Map<String, Object> others;

    @Getter
    @Setter
    public static class FileInfo {
        private MediaType mediaType;
        private String fileUrl;
        private String fileName;
        private Map<String, Object> others;
    }

}
