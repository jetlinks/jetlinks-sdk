package org.jetlinks.sdk.generator.entity;

import com.github.javaparser.ast.expr.AnnotationExpr;
import lombok.Getter;
import lombok.Setter;

import java.sql.JDBCType;

/**
 * @author gyl
 * @since 1.0.1
 */
@Getter
@Setter
public class RdbColumn extends Column {


    //---- @Column ----
    private boolean updatable;

    private Integer length;

    private Integer precision;

    private Integer scale;

    //---- @DefaultValue ----

    private String defaultValue;

    //---- @ColumnType ----

    private JDBCType jdbcType;

    private String javaType;

    private AnnotationExpr codec;


}
