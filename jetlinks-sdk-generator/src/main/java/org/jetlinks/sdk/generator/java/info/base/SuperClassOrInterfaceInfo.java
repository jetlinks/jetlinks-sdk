package org.jetlinks.sdk.generator.java.info.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SuperClassOrInterfaceInfo {

    @Schema(description = "导入路径")
    private String packagePath;

    @Schema(description = "泛型信息")
    private List<String> generics;

    @Schema(description = "名称")
    private String name;
}
