package org.jetlinks.sdk.server.ai.llm.agent.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.sdk.server.ai.llm.agent.ChatRequest;
import org.jetlinks.sdk.server.ai.llm.agent.ChatResponse;

import java.util.function.Supplier;

@AllArgsConstructor
@Getter
public enum DefaultChatType {

    generation("generation", AiGenerationChatRequest::new, AiGenerationChatResponse::new),
    ;
    private final String type;
    private final Supplier<? extends ChatRequest<?>> request;
    private final Supplier<? extends ChatResponse<?>> response;

    @SuppressWarnings("all")
    public <T extends ChatRequest<?>> T createRequest() {
        ChatRequest<?> req = this.request.get();
        req.setType(this.type);
        return (T) req;
    }

    @SuppressWarnings("all")
    public <T extends ChatResponse<?>> T createResponse() {
        ChatResponse<?> res = this.response.get();
        res.setType(this.type);
        return (T) res;
    }

    @SuppressWarnings("all")
    public <T extends ChatResponse<?>> T covert(ChatResponse<?> response) {
        ChatResponse<?> copy = FastBeanCopier.copy(response, this.response);
        copy.setType(this.type);
        return (T) copy;
    }

    @SuppressWarnings("all")
    public <T extends ChatRequest<?>> T covert(ChatRequest<?> response) {
        ChatRequest<?> copy = FastBeanCopier.copy(response, this.request);
        copy.setType(this.type);
        return (T) copy;
    }

}
