package org.jetlinks.sdk.generator.java.utils;

import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.Name;

import java.util.Map;

public class AnnotationUtils {

    /**
     * 创建有配置的注解
     *
     * @param annotationName 注解名称
     * @param properties     注解属性
     * @return AnnotationExpr
     */
    public static AnnotationExpr createNormalAnnotation(String annotationName, Map<String, Object> properties) {
        return null;
    }

    /**
     * 创建无配置的注解
     *
     * @return AnnotationExpr
     */
    public static AnnotationExpr createMarkAnnotation(String annotationName) {
        return new MarkerAnnotationExpr(new Name(annotationName));
    }
}
