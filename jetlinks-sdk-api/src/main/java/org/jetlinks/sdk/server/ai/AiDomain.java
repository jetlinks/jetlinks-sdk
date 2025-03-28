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

    ComputerVision("计算机视觉");

    private final String text;


    @Override
    public String getValue() {
        return name();
    }
}
