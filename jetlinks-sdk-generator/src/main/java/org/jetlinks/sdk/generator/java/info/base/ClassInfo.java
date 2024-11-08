package org.jetlinks.sdk.generator.java.info.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ClassInfo {

    @Schema(description = "类名")
    private String name;

    @Schema(description = "字段信息")
    private List<FieldInfo> fields = new ArrayList<>();

    @Schema(description = "注解信息")
    private List<AnnotationInfo> annotations = new ArrayList<>();

    @Schema(description = "父类信息")
    private SuperClassOrInterfaceInfo superClass;

    @Schema(description = "实现的接口信息")
    private List<SuperClassOrInterfaceInfo> interfaces = new ArrayList<>();
}
