package org.jetlinks.sdk.generator.java.enums;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.hswebframework.web.bean.FastBeanCopier;
import org.hswebframework.web.dict.EnumDict;
import org.jetlinks.sdk.generator.java.info.ColumnInfo;

import static org.jetlinks.sdk.generator.java.constant.ClassOrInterfaceConstant.*;

/**
 * 普通实体类注解
 */
@Getter
@AllArgsConstructor
public enum ColumnAnnotation implements EnumDict<String> {
    size("@Size注解") {
        @Override
        public AnnotationExpr createAnnotation(Object value) {
            ColumnInfo.SizeSpec sizeSpec = FastBeanCopier.copy(value, new ColumnInfo.SizeSpec());
            NodeList<MemberValuePair> nodeList = new NodeList<>();
            if (StringUtils.isNotBlank(sizeSpec.getMax())) {
                nodeList.add(new MemberValuePair("max", new IntegerLiteralExpr(sizeSpec.getMax())));
            }
            if (StringUtils.isNotBlank(sizeSpec.getMin())) {
                nodeList.add(new MemberValuePair("min", new IntegerLiteralExpr(sizeSpec.getMin())));
            }
            return new NormalAnnotationExpr(new Name(SIZE), nodeList);
        }
    },
    pattern("@Pattern注解") {
        @Override
        public AnnotationExpr createAnnotation(Object value) {
            NodeList<MemberValuePair> nodeList = new NodeList<>();
            nodeList.add(new MemberValuePair("regexp", new StringLiteralExpr(String.valueOf(value))));
            return new NormalAnnotationExpr(new Name(PATTERN), nodeList);
        }
    },
    max("@Max注解") {
        @Override
        public AnnotationExpr createAnnotation(Object value) {
            return new SingleMemberAnnotationExpr(new Name(MAX), new LongLiteralExpr(String.valueOf(value)));
        }
    },
    min("@Min注解") {
        @Override
        public AnnotationExpr createAnnotation(Object value) {
            return new SingleMemberAnnotationExpr(new Name(MIN), new LongLiteralExpr(String.valueOf(value)));
        }
    },

    getter("@Getter注解") {
        @Override
        public AnnotationExpr createAnnotation(Object value) {
            return new MarkerAnnotationExpr(new Name(GETTER));
        }
    },

    setter("@Setter注解") {
        @Override
        public AnnotationExpr createAnnotation(Object value) {
            return new MarkerAnnotationExpr(new Name(SETTER));
        }
    },
    notnull("@NotNull注解") {
        @Override
        public AnnotationExpr createAnnotation(Object value) {
            return new MarkerAnnotationExpr(new Name(NOT_NULL));
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
