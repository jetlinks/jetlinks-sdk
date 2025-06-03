package org.jetlinks.sdk.generator.java.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EnumInfo extends FieldInfo {

    @Schema(description = "枚举类参数")
    private List<ArgumentsInfo> arguments;

    @Schema(description = "方法信息")
    private List<MethodInfo> methods;

    public EnumInfo withArguments(List<ArgumentsInfo> arguments) {
        this.setArguments(arguments);
        return this;
    }

    public EnumInfo withMethods(List<MethodInfo> methods) {
        this.setMethods(methods);
        return this;
    }

    public static EnumInfo of(String id) {
        EnumInfo enumInfo = of();
        enumInfo.setId(id);
        return enumInfo;
    }

    public static EnumInfo of() {
        return new EnumInfo();
    }
}
