package org.jetlinks.sdk.generator.java.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArgumentsInfo {

    @Schema(description = "参数类型")
    private ClassInfo type;

    @Schema(description = "参数值")
    private Object value;

    public static ArgumentsInfo of(ClassInfo type, Object value) {
        ArgumentsInfo argumentsInfo = new ArgumentsInfo();
        argumentsInfo.setType(type);
        argumentsInfo.setValue(value);
        return argumentsInfo;
    }
}
