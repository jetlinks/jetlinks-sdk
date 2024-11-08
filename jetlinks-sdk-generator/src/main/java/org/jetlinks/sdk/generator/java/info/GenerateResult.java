package org.jetlinks.sdk.generator.java.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class GenerateResult {

    @Schema(description = "包名")
    private String classPackage;

    @Schema(description = "类名")
    private String classSimpleName;

    @Schema(description = "生成的代码信息")
    private String generate;


}