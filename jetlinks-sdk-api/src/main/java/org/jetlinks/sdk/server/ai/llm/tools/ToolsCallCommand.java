package org.jetlinks.sdk.server.ai.llm.tools;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Mono;

import java.util.Map;

@Schema(title = "调用工具")
public class ToolsCallCommand extends AbstractCommand<Mono<Object>,ToolsCallCommand> {

    @Schema(title = "工具名称")
    public String getName(){
        return getOrNull("name",String.class);
    }

    @Schema(title = "参数")
    public Map<String,Object> getArguments(){
        return getOrNull("arguments",Map.class);
    }

}
