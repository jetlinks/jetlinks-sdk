package org.jetlinks.sdk.server.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.dict.I18nEnumDict;
import org.jetlinks.sdk.server.ai.cv.ObjectDetectionResult;

import java.util.function.Supplier;

/**
 * AI领域
 */
@AllArgsConstructor
@Getter
public enum AiDomain implements I18nEnumDict<String> {

    ComputerVision("计算机视觉"),
    // 基于大语言模型的智能体
    LLMAgent("智能体"),
    // 大语言模型
    LLM("大语言模型");

    private final String text;

    public Supplier<? extends AiOutput<?>> getAiOutputInstance() {
        return ObjectDetectionResult::new;
    }

    public Supplier<? extends AiOutput<?>> getSimpleAiOutputInstance() {
        return SimpleGenericAiOutput::new;
    }

    @Override
    public String getValue() {
        return name();
    }
}
