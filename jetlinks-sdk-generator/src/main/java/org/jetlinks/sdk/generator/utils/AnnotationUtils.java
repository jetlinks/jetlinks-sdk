package org.jetlinks.sdk.generator.utils;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.*;
import org.apache.commons.lang3.StringUtils;

/**
 * 添加注释工具类
 *
 * @author gyl
 * @since 1.0.1
 */
public class AnnotationUtils {

    public static class Lombok {
        /**
         * 添加 {@link  lombok.Getter }注解
         */
        public static void getter(FieldDeclaration field) {
            AnnotationExpr annotationExpr = new MarkerAnnotationExpr("Getter");
            field.addAnnotation(annotationExpr);
        }

        /**
         * 添加 {@link  lombok.Setter }注解
         */
        public static void setter(FieldDeclaration field) {
            AnnotationExpr annotationExpr = new MarkerAnnotationExpr("Setter");
            field.addAnnotation(annotationExpr);
        }
    }

    public static class Validation {
        /**
         * 添加 {@link javax.validation.constraints.NotBlank }注解
         */
        public static void notBlank(FieldDeclaration field) {
            AnnotationExpr annotationExpr = new MarkerAnnotationExpr("NotBlank");
            field.addAnnotation(annotationExpr);
        }

        /**
         * 添加{@link javax.validation.constraints.NotNull } 注解
         */
        public static void notNull(FieldDeclaration field) {
            AnnotationExpr annotationExpr = new MarkerAnnotationExpr("NotNull");
            field.addAnnotation(annotationExpr);
        }

        /**
         * 添加{@link javax.validation.constraints.Size } 注解，带有 min 和 max 参数
         */
        public static void size(FieldDeclaration field, String min, String max) {
            NormalAnnotationExpr annotationExpr = new NormalAnnotationExpr();
            annotationExpr.setName("Size");
            if (StringUtils.isNotBlank(min)) {
                annotationExpr.addPair("min", new IntegerLiteralExpr(min));
            }
            if (StringUtils.isNotBlank(max)) {
                annotationExpr.addPair("max", new IntegerLiteralExpr(max));
            }
            field.addAnnotation(annotationExpr);
        }

        /**
         * 添加 {@link javax.validation.constraints.Min }注解，带有 value 参数
         */
        public static void min(FieldDeclaration field, String value) {
            NormalAnnotationExpr annotationExpr = new NormalAnnotationExpr();
            annotationExpr.setName("Min");
            annotationExpr.addPair("value", new IntegerLiteralExpr(value));
            field.addAnnotation(annotationExpr);
        }

        /**
         * 添加 {@link javax.validation.constraints.Max }注解，带有 value 参数
         */
        public static void max(FieldDeclaration field, String value) {
            NormalAnnotationExpr annotationExpr = new NormalAnnotationExpr();
            annotationExpr.setName("Max");
            annotationExpr.addPair("value", new IntegerLiteralExpr(value));
            field.addAnnotation(annotationExpr);
        }

        /**
         * 添加 {@link javax.validation.constraints.Pattern }注解，带有正则表达式 pattern 参数
         */
        public static void pattern(FieldDeclaration field, String regex) {
            NormalAnnotationExpr annotationExpr = new NormalAnnotationExpr();
            annotationExpr.setName("Pattern");
            annotationExpr.addPair("regexp", new StringLiteralExpr(regex));
            field.addAnnotation(annotationExpr);
        }
    }


}
