package org.jetlinks.sdk.generator.java.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class PackageInfo {

    @Schema(description = "包路径")
    private String name;

    @Schema(description = "类信息")
    private List<ClassInfo> classes;
}
