package org.jetlinks.sdk.server.ai;

import reactor.core.publisher.Mono;

import java.util.function.Supplier;

/**
 * AI 任务目标
 *
 * @see InternalCVTaskTarget
 * @see AiCommand
 * @since 1.0.1
 */
public interface TaskTarget {

    String getValue();

    String getText();

    String getDescription();

    Supplier<? extends AiOutput<?>> getAiOutputInstance();

    Supplier<? extends AiOutput<?>> getSimpleAiOutputInstance();

    default Mono<AiOutputMetadata> getAiOutputMetadata() {
        return Mono.empty();
    }

}
