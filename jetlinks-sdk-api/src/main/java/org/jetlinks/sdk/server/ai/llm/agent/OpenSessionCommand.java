package org.jetlinks.sdk.server.ai.llm.agent;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractStreamCommand;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.ai.llm.LlmMessage;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import org.jetlinks.supports.utils.MetadataConverter;
import reactor.core.publisher.Flux;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Schema(title = "开启会话", description = "开启会话并进行流式输入输出.")
public class OpenSessionCommand extends AbstractStreamCommand<LlmMessage<?>, LlmMessage<?>, OpenSessionCommand> {

    @Nonnull
    @Override
    public Flux<LlmMessage<?>> stream() {
        if (stream != null) {
            return stream;
        }
        Object msg = getOrNull("message", Object.class);
        if (msg != null) {
            return Flux.just(convertStreamValue(msg));
        }
        return Flux.empty();
    }

    @Schema(title = "AgentId")
    public String getAgentId() {
        return getOrNull("agentId", String.class);
    }

    public OpenSessionCommand setAgentId(String id) {
        return with("agentId", id);
    }

    @Schema(title = "SessionId", description = "为空则开启新会话")
    public String getSessionId() {
        return getOrNull("sessionId", String.class);
    }

    public OpenSessionCommand setSessionId(String sessionId) {
        return with("sessionId", sessionId);
    }

    @Schema(title = "参数", description = "智能体运行所需的参数")
    @SuppressWarnings("unchecked")
    public Map<String, Object> getParameters() {
        Map<String, Object> params = getOrNull("parameters", Map.class);

        return params == null ? Collections.emptyMap() : params;
    }

    public OpenSessionCommand setParameters(Map<String, Object> parameters) {
        return with("parameters", parameters);
    }

    @Override
    public LlmMessage<?> convertStreamValue(Object value) {
        return super.convertStreamValue(value);
    }

    @Schema(title = "工具集描述", description = "提供给本次会话的工具集")
    public List<FunctionMetadata> getTools() {
        Object tools = getOrNull("tools", Object.class);
        return ConverterUtils.convertToList(tools, MetadataConverter::convertToFunctionMetadata);
    }

    public OpenSessionCommand setTools(List<FunctionMetadata> tools) {
        return with("tools", tools);
    }

    @Schema(title = "额外配置", description = "智能体运行所需的额外配置")
    @SuppressWarnings("unchecked")
    public Map<String, Object> getExpands() {
        Map<String, Object> params = getOrNull("expands", Map.class);

        return params == null ? Collections.emptyMap() : params;
    }

    public OpenSessionCommand setExpands(Map<String, Object> expands) {
        return with("expands", expands);
    }

}
