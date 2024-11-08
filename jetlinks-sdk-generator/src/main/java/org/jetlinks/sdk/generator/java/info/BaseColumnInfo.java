package org.jetlinks.sdk.generator.java.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseColumnInfo {

    @Schema(description = "字段")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "类型")
    private String typeClass;


}
