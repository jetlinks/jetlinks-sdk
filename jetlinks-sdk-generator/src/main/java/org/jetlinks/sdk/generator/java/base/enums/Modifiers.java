package org.jetlinks.sdk.generator.java.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.dict.EnumDict;

@Getter
@AllArgsConstructor
public enum Modifiers implements EnumDict<String> {
    DEFAULT("default"),
    PUBLIC("public"),
    PROTECTED("protected"),
    ABSTRACT("abstract"),
    STATIC("static"),
    FINAL("final"),
    PRIVATE("private");
    private final String text;

    @Override
    public String getValue() {
        return name();
    }
}
