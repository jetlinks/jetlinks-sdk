package org.jetlinks.sdk.generator.java.utils;

import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.TypeParameter;
import org.jetlinks.sdk.generator.java.base.ClassInfo;

import java.util.Map;

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
            NameExpr scope = fieldAccessExpr.getScope().asNameExpr();
            return String.join(".", scope.getNameAsString(), fieldAccessExpr.getNameAsString());
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
            classInfo = ClassInfo.of(className, importMap.get(className));
        } else if (expression.isArrayInitializerExpr()) {
            classInfo = ClassInfo.of("Array");
        } else if (expression.isFieldAccessExpr()) {
            FieldAccessExpr fieldAccessExpr = expression.asFieldAccessExpr();
            NameExpr scope = fieldAccessExpr.getScope().asNameExpr();
            String fieldName = String.join(".", scope.getNameAsString(), fieldAccessExpr.getNameAsString());
            classInfo = ClassInfo.of(fieldName, importMap.get(scope.getNameAsString()));
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
            case "Class":
                return new ClassExpr(new TypeParameter(valueStr));
            default:
                String intactClassName = ClassInfo.getIntactClassName(classInfo);
                if (valueStr.contains(".")) {
                    String[] split = valueStr.split("\\.");
                    return new FieldAccessExpr(new NameExpr(intactClassName), split[split.length - 1]);
                } else {
                    return new FieldAccessExpr(new NameExpr(intactClassName), valueStr);
                }

        }

    }
}
