package org.jetlinks.sdk.server.knowledge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.dict.EnumDict;

@AllArgsConstructor
@Getter
public enum SourceType implements EnumDict<String> {
    system("系统"),
    manual("手动"),
    ai("ai");
    private final String text;

    @Override
    public String getValue() {
        return name();
    }
}
