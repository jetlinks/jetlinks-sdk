package org.jetlinks.sdk.server.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.dict.I18nEnumDict;

/**
 * AI领域
 */
@AllArgsConstructor
@Getter
public enum AiDomain implements I18nEnumDict<String> {

    ComputerVision("计算机视觉") {

        @Override
        public TaskTarget createSimpleTaskTarget() {
            return new SimpleComputerVisionTaskTarget();
        }

        @Override
        public TaskTarget createSimpleTaskTarget(String value, String text, String description) {
            return SimpleComputerVisionTaskTarget.of(value, text, description);
        }
    },
    // 基于大语言模型的智能体
    LLMAgent("智能体"),
    // 大语言模型
    LLM("大语言模型");

    private final String text;

    public TaskTarget createSimpleTaskTarget() {
        return new SimpleTaskTarget();
    }

    public TaskTarget createSimpleTaskTarget(String value, String text, String description) {
        return SimpleTaskTarget.of(value, text, description);
    }


    @Override
    public String getValue() {
        return name();
    }
}
