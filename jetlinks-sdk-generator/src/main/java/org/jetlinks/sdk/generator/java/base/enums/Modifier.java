package org.jetlinks.sdk.generator.java.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.dict.EnumDict;

@Getter
@AllArgsConstructor
public enum Modifier implements EnumDict<String> {
    DEFAULT("default"),
    PUBLIC("public"),
    PROTECTED("protected"),
    PRIVATE("private");
    private final String text;

    @Override
    public String getValue() {
        return name();
    }
}
