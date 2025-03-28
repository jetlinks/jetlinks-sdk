package org.jetlinks.sdk.generator.java.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AnnotatedElementInfo {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "注解信息")
    private List<AnnotationInfo> annotations;
}
