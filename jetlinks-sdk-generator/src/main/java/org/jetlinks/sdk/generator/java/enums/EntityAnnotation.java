package org.jetlinks.sdk.generator.java.enums;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.TypeParameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.hswebframework.web.bean.FastBeanCopier;
import org.hswebframework.web.dict.EnumDict;
import org.jetlinks.sdk.generator.java.info.RdbColumnInfo;

import java.util.Objects;

import static org.jetlinks.sdk.generator.java.constant.ClassOrInterfaceConstant.*;

/**
 * 数据库实体类相关注解
 */
@Getter
@AllArgsConstructor
public enum EntityAnnotation implements EnumDict<String> {
    column("@Column注解") {
        @Override
        public AnnotationExpr createAnnotation(Object value) {
            RdbColumnInfo.ColumnSpec columnSpec = FastBeanCopier.copy(value, new RdbColumnInfo.ColumnSpec());
            NodeList<MemberValuePair> nodeList = new NodeList<>();
            if (Objects.nonNull(columnSpec.getNullable())) {
                nodeList.add(new MemberValuePair("nullable", new BooleanLiteralExpr(columnSpec.getNullable())));
            }
            if (Objects.nonNull(columnSpec.getUpdatable())) {
                nodeList.add(new MemberValuePair("updatable", new BooleanLiteralExpr(columnSpec.getUpdatable())));
            }
            if (StringUtils.isNotBlank(columnSpec.getLength())) {
                nodeList.add(new MemberValuePair("length", new IntegerLiteralExpr(columnSpec.getLength())));
            }
            if (StringUtils.isNotBlank(columnSpec.getPrecision())) {
                nodeList.add(new MemberValuePair("precision", new IntegerLiteralExpr(columnSpec.getPrecision())));
            }
            if (StringUtils.isNotBlank(columnSpec.getScale())) {
                nodeList.add(new MemberValuePair("scale", new IntegerLiteralExpr(columnSpec.getScale())));
            }
            return new NormalAnnotationExpr(new Name(COLUMN), nodeList);
        }
    },
    defaultValue("@DefaultValue注解") {
        @Override
        public AnnotationExpr createAnnotation(Object value) {
            return new SingleMemberAnnotationExpr(new Name(DEFAULT_VALUE), new StringLiteralExpr(String.valueOf(value)));
        }

    },
    columnType("@ColumnType注解") {
        @Override
        public AnnotationExpr createAnnotation(Object value) {
            RdbColumnInfo.ColumnTypeSpec columnTypeSpec = FastBeanCopier.copy(value, new RdbColumnInfo.ColumnTypeSpec());
            NodeList<MemberValuePair> nodeList = new NodeList<>();

            nodeList.add(new MemberValuePair("jdbcType",
                                             new FieldAccessExpr(new NameExpr("JDBCType"),
                                                                 columnTypeSpec
                                                                         .getJdbcType()
                                                                         .getName())));
            nodeList.add(new MemberValuePair("javaType", new ClassExpr(new TypeParameter(columnTypeSpec.getJavaType()))));
            return new NormalAnnotationExpr(new Name(COLUMN_TYPE), nodeList);
        }
    },
    table("@Table注解") {
        @Override
        public AnnotationExpr createAnnotation(Object value) {
            return new SingleMemberAnnotationExpr(new Name(TABLE), new StringLiteralExpr(String.valueOf(value)));
        }
    },
    enableEntityEvent("@EnableEntityEvent注解") {
        @Override
        public AnnotationExpr createAnnotation(Object value) {
            return new MarkerAnnotationExpr(new Name(ENABLE_ENTITY_EVENT));
        }
    },
    schema("@Schema注解") {
        @Override
        public AnnotationExpr createAnnotation(Object value) {
            NodeList<MemberValuePair> nodeList = new NodeList<>();
            nodeList.add(new MemberValuePair("description", new StringLiteralExpr(String.valueOf(value))));
            return new NormalAnnotationExpr(new Name(SCHEMA), nodeList);
        }
    };

    private final String text;

    /**
     * 创建注解
     *
     * @param value 注解配置值
     * @return AnnotationExpr
     */
    public abstract AnnotationExpr createAnnotation(Object value);


    @Override
    public String getValue() {
        return name();
    }
}
