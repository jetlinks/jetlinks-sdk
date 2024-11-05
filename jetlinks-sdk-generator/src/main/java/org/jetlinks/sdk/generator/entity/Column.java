package org.jetlinks.sdk.generator.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.metadata.DataType;

/**
 * @author gyl
 * @since 1.0.1
 */
public class Column {
    @Schema(description = "字段")
    private String id;
    @Schema(description = "名称")
    private String name;
    @Schema(description = "类型")
    private String typeClass;
    @Schema(description = "非空")
    private boolean notNull;
    @Schema(description = "最大值")
    private String max;
    @Schema(description = "最小值")
    private String min;
    @Schema(description = "最大长度")
    private String maxSize;
    @Schema(description = "最小长度")
    private String minSize;
    @Schema(description = "正则")
    private String pattern;

    public static Column of(DataType dataType) {
        // TODO: 2024/11/5
        return null;
    }

}
