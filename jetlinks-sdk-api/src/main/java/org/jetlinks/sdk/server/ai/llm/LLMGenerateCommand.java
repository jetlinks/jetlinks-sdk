package org.jetlinks.sdk.server.ai.llm;

import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.ai.AiCommand;
import reactor.core.publisher.Flux;

public class LLMGenerateCommand extends AbstractCommand<Flux<LLMGenerateOutput>, LLMGenerateCommand>
    implements AiCommand<LLMGenerateOutput> {

    public Object getMessage() {
        return getOrNull("message", Object.class);
    }


}
