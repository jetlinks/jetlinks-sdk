package org.jetlinks.sdk.generator.java.info.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PackageInfo {

    @Schema(description = "包名")
    private String name;

    @Schema(description = "类信息")
    private List<ClassInfo> classes;
}
