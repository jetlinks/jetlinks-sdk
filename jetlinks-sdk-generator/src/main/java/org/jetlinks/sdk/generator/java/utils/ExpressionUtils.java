package org.jetlinks.sdk.generator.java.utils;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.TypeParameter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetlinks.sdk.generator.java.base.AnnotationInfo;
import org.jetlinks.sdk.generator.java.base.ArgumentsInfo;
import org.jetlinks.sdk.generator.java.base.ClassInfo;
import org.jetlinks.sdk.server.utils.ConverterUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 表达式转换工具类
 */
public class ExpressionUtils {

    /**
     * 获取对应类型表达式的值
     *
     * @param expression 目标表达式
     * @return 表达式值
     */
    public static Object getExpressionValue(Expression expression) {
        if (expression.isStringLiteralExpr()) {
            return expression.asStringLiteralExpr().getValue();
        } else if (expression.isIntegerLiteralExpr()) {
            return expression.asIntegerLiteralExpr().getValue();
        } else if (expression.isBooleanLiteralExpr()) {
            return expression.asBooleanLiteralExpr().getValue();
        } else if (expression.isClassExpr()) {
            return expression.asClassExpr().getTypeAsString();
        } else if (expression.isArrayInitializerExpr()) {
            return expression.asArrayInitializerExpr().getValues();
        } else if (expression.isFieldAccessExpr()) {
            FieldAccessExpr fieldAccessExpr = expression.asFieldAccessExpr();
            Expression scope = fieldAccessExpr.getScope();
            String scopeName;
            if (fieldAccessExpr.getScope().isNameExpr()) {
                scopeName = scope.asNameExpr().getNameAsString();
            } else {
                scopeName = String.valueOf(getExpressionValue(scope));
            }

            return String.join(".", scopeName, fieldAccessExpr.getNameAsString());
        } else {
            return expression;
        }
    }

    /**
     * 根据表达式，获取注解对应的类描述信息
     *
     * @param expression 表达式
     * @param importMap  导包Map
     * @return ClassInfo
     */
    public static ClassInfo getExpressionClassInfo(Expression expression, Map<String, String> importMap) {
        ClassInfo classInfo;
        if (expression.isStringLiteralExpr()) {
            classInfo = ClassInfo.of("String");
        } else if (expression.isIntegerLiteralExpr()) {
            classInfo = ClassInfo.of("Integer");
        } else if (expression.isBooleanLiteralExpr()) {
            classInfo = ClassInfo.of("Boolean");
        } else if (expression.isClassExpr()) {
            String className = expression.asClassExpr().getTypeAsString();
            String clazzPackage = Optional
                .ofNullable(importMap.get(className))
                .orElseGet(() -> String.join(".", importMap.get("classPackage"), className));
            classInfo = ClassInfo.of("Class", clazzPackage);
        } else if (expression.isArrayInitializerExpr()) {
            classInfo = ClassInfo.of("Array");
        } else if (expression.isFieldAccessExpr()) {
            FieldAccessExpr fieldAccessExpr = expression.asFieldAccessExpr();
            Expression scope = fieldAccessExpr.getScope();
            String scopeName;
            if (fieldAccessExpr.getScope().isNameExpr()) {
                scopeName = scope.asNameExpr().getNameAsString();
            } else {
                scopeName = String.valueOf(getExpressionValue(scope));
            }
            String fieldName = String.join(".", scopeName, fieldAccessExpr.getNameAsString());
            classInfo = ClassInfo.of(fieldName, importMap.get(scopeName));
        } else {
            classInfo = null;
        }
        return classInfo;
    }

    /**
     * 根据类描述信息和值，获取对应的表达式
     *
     * @param classInfo 类描述信息
     * @param value     表达式值
     * @return Expression
     */
    public static Expression getExpression(ClassInfo classInfo, Object value) {
        String type = classInfo.getName();
        String valueStr = String.valueOf(value);
        switch (type) {
            case "String":
                return new StringLiteralExpr(valueStr);
            case "Integer":
                return new IntegerLiteralExpr(valueStr);
            case "Boolean":
                return new BooleanLiteralExpr(Boolean.parseBoolean(valueStr));
            case "Long":
                return new LongLiteralExpr(valueStr);
            case "Double":
                return new DoubleLiteralExpr(valueStr);
            case "Character":
                return new CharLiteralExpr(valueStr);
            case "Class":
                if (value instanceof ClassInfo) {
                    ClassInfo clazz = (ClassInfo) value;
                    return new ClassExpr(new TypeParameter(clazz.getName()));
                }
                return new ClassExpr(new TypeParameter(valueStr));
            case "Array":
                List<?> valueList = (List<?>) value;
                if (CollectionUtils.isEmpty(valueList)) {
                    return new ArrayInitializerExpr();
                }
                List<Expression> expressionList = ConverterUtils
                        .convertToList(valueList, obj -> {
                            if (obj instanceof AnnotationInfo) {
                                return AnnotationExpressionUtils.toAnnotationExpr((AnnotationInfo) obj);
                            } else if (obj instanceof ClassInfo) {
                                ClassInfo clazz = (ClassInfo) obj;
                                if (StringUtils.contains(clazz.getName(), ".")) {
                                    // 处理枚举类型
                                    return getExpression(clazz, clazz.getName());
                                }
                                return getExpression(ClassInfo.of("Class", clazz.getClassPackage()), clazz.getName());
                            } else {
                                return getExpression(ClassInfo.of(obj.getClass().getSimpleName()), obj);
                            }
                        });
                return new ArrayInitializerExpr(new NodeList<>(expressionList));

            default:
                String intactClassName = ClassInfo.getIntactClassName(classInfo);
                if (intactClassName.contains(".")) {
                    intactClassName = intactClassName.substring(0, intactClassName.lastIndexOf("."));
                }
                if (valueStr.contains(".")) {
                    String[] split = valueStr.split("\\.");
                    return new FieldAccessExpr(new NameExpr(intactClassName), split[split.length - 1]);
                } else {
                    return new FieldAccessExpr(new NameExpr(intactClassName), valueStr);
                }

        }

    }

    public static ArgumentsInfo getExpressionArgumentsInfos(Expression expression, Map<String, String> importMap) {
        ClassInfo classInfo = getExpressionClassInfo(expression, importMap);
        Object expressionValue = getExpressionValue(expression);
        return ArgumentsInfo.of(classInfo, expressionValue);
    }

    public static List<ArgumentsInfo> getExpressionArgumentsInfos(List<Expression> expressions, Map<String, String> importMap) {
        return expressions
                .stream()
                .map(expression -> getExpressionArgumentsInfos(expression, importMap))
                .collect(Collectors.toList());
    }
}
