package org.jetlinks.sdk.server.knowledge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.dict.I18nEnumDict;

@AllArgsConstructor
@Getter
public enum ResourceStatus implements I18nEnumDict<String> {
    disable("禁用"),
    enable("启用");

    private final String text;

    @Override
    public String getValue() {
        return name();
    }

}