package org.jetlinks.sdk.generator.java.utils;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.TypeParameter;
import org.apache.commons.collections4.CollectionUtils;
import org.jetlinks.sdk.generator.java.base.AnnotationInfo;
import org.jetlinks.sdk.generator.java.base.AnnotationProperty;

import java.util.ArrayList;
import java.util.List;

public class AnnotationUtils {

    /**
     * 根据注解信息，多个注解
     *
     * @param annotationInfos 注解信息集合
     * @return List<AnnotationExpr>
     */
    public static List<AnnotationExpr> createAnnotation(List<AnnotationInfo> annotationInfos) {
        List<AnnotationExpr> annotations = new ArrayList<>();
        for (AnnotationInfo annotationInfo : annotationInfos) {
            String name = annotationInfo.getName();
            List<AnnotationProperty> properties = annotationInfo.getProperties();
            AnnotationExpr annotationExpr;
            if (CollectionUtils.isEmpty(properties)) {
                annotationExpr = createMarkAnnotation(name);
            } else if (properties.size() == 1) {
                annotationExpr = createSingleAnnotation(name, properties.get(0));
            } else {
                annotationExpr = createNormalAnnotation(name, properties);
            }
            annotations.add(annotationExpr);
        }
        return annotations;
    }

    /**
     * 创建多配置的注解
     * @param name 注解名称
     * @param properties 注解配置
     * @return AnnotationExpr
     */
    public static AnnotationExpr createNormalAnnotation(String name, List<AnnotationProperty> properties) {
        NodeList<MemberValuePair> nodeList = new NodeList<>();
        for (AnnotationProperty property : properties) {
            Expression expression = getExpression(property.getType().getName(),
                                                  property.getDefaultValue());
            MemberValuePair memberValuePair = new MemberValuePair(property.getName(), expression);
            nodeList.add(memberValuePair);
        }
        return new NormalAnnotationExpr(new Name(name), nodeList);
    }

    /**
     * 创建单配置的注解
     *
     * @param name 注解名称
     * @param property 注解配置
     * @return AnnotationExpr
     */
    public static AnnotationExpr createSingleAnnotation(String name, AnnotationProperty property) {
        Expression expression = getExpression(property.getType().getName(),
                                              property.getDefaultValue());
        return new SingleMemberAnnotationExpr(new Name(name), expression);
    }

    /**
     * 创建无配置的注解
     *
     * @return AnnotationExpr
     */
    public static AnnotationExpr createMarkAnnotation(String annotationName) {
        return new MarkerAnnotationExpr(new Name(annotationName));
    }

    private static Expression getExpression(String type, Object value) {
        String valueStr = String.valueOf(value);
        switch (type) {
            case "String":
                return new StringLiteralExpr(valueStr);
            case "Integer":
                return new IntegerLiteralExpr(valueStr);
            case "Boolean":
                return new BooleanLiteralExpr(Boolean.parseBoolean(valueStr));
            case "Class":
                return new ClassExpr(new TypeParameter(valueStr));
            case "Enum":
                if (valueStr.contains(".")) {
                    String[] split = valueStr.split("\\.");
                    return new FieldAccessExpr(new NameExpr(split[0]), split[1]);
                }
            default:
                return null;
        }

    }
}
