package org.jetlinks.sdk.generator.java.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ColumnInfo extends BaseColumnInfo {

    @Schema(description = "正则")
    private String pattern;

    @Schema(description = "最大值")
    private String max;

    @Schema(description = "最小值")
    private String min;

    @Schema(description = "非空")
    private Boolean notnull;

    @Schema(description = "字段长度描述信息")
    private SizeSpec sizeSpec;

    //---- @Size ----
    @Setter
    @Getter
    public static class SizeSpec {
        @Schema(description = "最大长度")
        private String max;

        @Schema(description = "最小长度")
        private String min;
    }
}
