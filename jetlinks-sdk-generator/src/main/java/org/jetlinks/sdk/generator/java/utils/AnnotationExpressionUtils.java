package org.jetlinks.sdk.generator.java.utils;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetlinks.sdk.generator.java.base.AnnotationInfo;
import org.jetlinks.sdk.generator.java.base.AnnotationProperty;
import org.jetlinks.sdk.generator.java.base.ClassInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 注解表达式、注解描述信息转换工具类
 */
public class AnnotationExpressionUtils {

    /**
     * 处理单个注解表达式
     *
     * @param annotation 注解表达式
     * @param importsMap 导包Map
     * @return AnnotationInfo
     */
    public static AnnotationInfo handleAnnotationExpression(AnnotationExpr annotation, Map<String, String> importsMap) {
        String annotationName = annotation.getName().getIdentifier();
        ClassInfo classInfo = ClassInfo.of(annotationName, importsMap.get(annotationName));
        List<AnnotationProperty> properties = null;
        if (annotation.isSingleMemberAnnotationExpr()) {
            Expression memberValue = annotation
                    .asSingleMemberAnnotationExpr()
                    .getMemberValue();
            properties = Collections.singletonList((handleSingleAnnotationValue(memberValue, importsMap)));
        } else if (annotation.isNormalAnnotationExpr()) {
            NodeList<MemberValuePair> pairs = annotation
                    .asNormalAnnotationExpr()
                    .getPairs();
            properties = handleNormalAnnotationValue(pairs, importsMap);
        }
        return AnnotationInfo.of(annotationName, classInfo, properties);

    }

    /**
     * 处理多个注解表达式
     *
     * @param annotations 注解表达式集合
     * @param importsMap  导包Map
     * @return List<AnnotationInfo>
     */
    public static List<AnnotationInfo> handleAnnotationExpression(List<AnnotationExpr> annotations, Map<String, String> importsMap) {
        return annotations
                .stream()
                .map(annotation -> handleAnnotationExpression(annotation, importsMap))
                .collect(Collectors.toList());

    }

    /**
     * 处理单属性值注解
     *
     * @param expression 属性值表达式
     * @param importsMap 导包Map
     * @return AnnotationProperty
     */
    public static AnnotationProperty handleSingleAnnotationValue(Expression expression, Map<String, String> importsMap) {
        Object propertyValue;
        //获取表达式值
        if (expression.isArrayInitializerExpr()) {
            propertyValue = handleAnnotationChildProperties(expression
                                                                    .asArrayInitializerExpr()
                                                                    .getValues(),
                                                            importsMap);
        } else {
            propertyValue = ExpressionUtils.getExpressionValue(expression);
        }
        return AnnotationProperty.of("value", propertyValue, ExpressionUtils.getExpressionClassInfo(expression, importsMap));
    }

    /**
     * 处理多属性值注解
     *
     * @param memberValues 多个属性值表达式
     * @param importsMap   导包Map
     * @return List<AnnotationProperty>
     */
    public static List<AnnotationProperty> handleNormalAnnotationValue(NodeList<MemberValuePair> memberValues, Map<String, String> importsMap) {
        List<AnnotationProperty> annotationProperties = new ArrayList<>();
        for (MemberValuePair pair : memberValues) {
            Expression pairValue = pair.getValue();
            String name = pair.getNameAsString();
            Object propertyValue;
            //获取表达式值
            if (pairValue.isArrayInitializerExpr()) {
                propertyValue = handleAnnotationChildProperties(pairValue
                                                                        .asArrayInitializerExpr()
                                                                        .getValues(),
                                                                importsMap);
            } else {
                propertyValue = ExpressionUtils.getExpressionValue(pair.getValue());
            }
            //获取表达式ClassInfo
            ClassInfo propertyClass = ExpressionUtils.getExpressionClassInfo(pairValue, importsMap);
            annotationProperties.add(AnnotationProperty.of(name, propertyValue, propertyClass));
        }
        return CollectionUtils.isEmpty(annotationProperties) ? null : annotationProperties;
    }

    /**
     * 处理注解属性值的子属性值
     *
     * @param childProperties 子属性值
     * @param importsMap      导包Map
     * @return Object
     */
    private static Object handleAnnotationChildProperties(NodeList<Expression> childProperties, Map<String, String> importsMap) {
        List<ClassInfo> childAnnotationClass = new ArrayList<>();
        List<AnnotationInfo> childAnnoExpression = new ArrayList<>();
        List<Object> childPrimitiveProperties = new ArrayList<>();
        for (Expression expression : childProperties) {
            if (expression.isClassExpr()) {
                childAnnotationClass.add(ExpressionUtils.getExpressionClassInfo(expression, importsMap));
            } else if (expression.isAnnotationExpr()) {
                childAnnoExpression.add(handleAnnotationExpression(expression.asAnnotationExpr(), importsMap));
            } else if (expression.isStringLiteralExpr()) {
                childPrimitiveProperties.add(ExpressionUtils.getExpressionValue(expression));
            }
        }

        if (CollectionUtils.isEmpty(childAnnotationClass)) {
            if (CollectionUtils.isEmpty(childAnnoExpression)) {
                return childPrimitiveProperties;
            } else {
                return childAnnoExpression;
            }
        } else {
            return childAnnotationClass;
        }

    }

    /**
     * 根据注解描述，构建多个注解
     *
     * @param annotationInfos 注解信息集合
     * @return List<AnnotationExpr>
     */
    public static List<AnnotationExpr> toAnnotationExprList(List<AnnotationInfo> annotationInfos) {
        if (CollectionUtils.isEmpty(annotationInfos)) {
            return new ArrayList<>();
        }
        List<AnnotationExpr> annotations = new ArrayList<>();
        for (AnnotationInfo annotationInfo : annotationInfos) {
            String name = annotationInfo.getName();
            List<AnnotationProperty> properties = annotationInfo.getProperties();
            AnnotationExpr annotationExpr;
            if (CollectionUtils.isEmpty(properties)) {
                annotationExpr = toMarkerAnnotationExpr(name);
            } else if (properties.size() == 1 && StringUtils.equals(properties.get(0).getName(), "value")) {
                annotationExpr = toSingleMemberAnnotationExpr(name, properties.get(0));
            } else {
                annotationExpr = toNormalAnnotationExpr(name, properties);
            }
            annotations.add(annotationExpr);
        }
        return annotations;
    }

    /**
     * 创建多配置的注解
     *
     * @param name       注解名称
     * @param properties 注解配置
     * @return AnnotationExpr
     */
    public static AnnotationExpr toNormalAnnotationExpr(String name, List<AnnotationProperty> properties) {
        NodeList<MemberValuePair> nodeList = new NodeList<>();
        for (AnnotationProperty property : properties) {
            Expression expression = ExpressionUtils.getExpression(property.getType(), property.getDefaultValue());
            MemberValuePair memberValuePair = new MemberValuePair(property.getName(), expression);
            nodeList.add(memberValuePair);
        }
        return new NormalAnnotationExpr(new Name(name), nodeList);
    }

    /**
     * 创建单配置的注解
     *
     * @param name     注解名称
     * @param property 注解配置
     * @return AnnotationExpr
     */
    public static AnnotationExpr toSingleMemberAnnotationExpr(String name, AnnotationProperty property) {
        Expression expression = ExpressionUtils.getExpression(property.getType(), property.getDefaultValue());
        return new SingleMemberAnnotationExpr(new Name(name), expression);
    }

    /**
     * 创建无配置的注解
     *
     * @return AnnotationExpr
     */
    public static AnnotationExpr toMarkerAnnotationExpr(String annotationName) {
        return new MarkerAnnotationExpr(new Name(annotationName));
    }
}
