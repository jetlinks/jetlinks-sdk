package org.jetlinks.sdk.generator.java.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.sql.JDBCType;

@Setter
@Getter
public class RdbColumnInfo extends BaseColumnInfo {
    @Schema(description = "实体类列描述信息")
    private ColumnSpec columnSpec;

    @Schema(description = "实体类列默认值描述信息")
    private String defaultValue;

    @Schema(description = "实体类列类型描述信息")
    private ColumnTypeSpec columnTypeSpec;


    //---- @Column ----
    @Getter
    @Setter
    public static class ColumnSpec {

        @Schema(description = "是否可为空")
        private Boolean nullable;

        @Schema(description = "是否可更新")
        private Boolean updatable;

        @Schema(description = "长度")
        private String length;

        @Schema(description = "小数位数")
        private String precision;

        @Schema(description = "精度")
        private String scale;
    }

    //---- @ColumnType ----
    @Getter
    @Setter
    public static class ColumnTypeSpec {

        @Schema(description = "数据库类型")
        private JDBCType jdbcType;

        @Schema(description = "Java类型")
        private String javaType;
    }


}
