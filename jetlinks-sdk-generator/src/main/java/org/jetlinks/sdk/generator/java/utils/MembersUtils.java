package org.jetlinks.sdk.generator.java.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetlinks.sdk.generator.java.base.*;
import org.jetlinks.sdk.generator.java.base.enums.Modifiers;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 成员（类中字段、方法、参数等）转换工具类
 */
public class MembersUtils {

    /**
     * 处理单个方法类型的成员
     *
     * @param method 方法声明
     * @param importsMap  导包Map
     * @return MethodInfo
     */
    public static MethodInfo handleMethodMember(MethodDeclaration method, Map<String, String> importsMap) {
        List<Modifiers> modifiers = handleModifier(method.getModifiers());
        List<AnnotationInfo> annotationInfos = AnnotationExpressionUtils.handleAnnotationExpression(method.getAnnotations(), importsMap);
        List<ParamInfo> paramInfos = handleParameterMember(method.getParameters(), importsMap);
        ClassInfo returnType = TypeUtils.handleClassOrInterface(method.getType(), importsMap);

        MethodInfo methodInfo = MethodInfo.of(method.getNameAsString(), annotationInfos, paramInfos, returnType, modifiers);
        method.getBody().ifPresent(blockStmt -> methodInfo.withBody(blockStmt.toString()));
        return methodInfo;
    }

    /**
     * 处理多个方法类型的成员
     *
     * @param declarations 方法声明
     * @param importsMap   导包Map
     * @return List<MethodInfo>
     */
    public static List<MethodInfo> handleMethodMember(List<MethodDeclaration> declarations, Map<String, String> importsMap) {
        return declarations
                .stream()
                .map(declaration -> handleMethodMember(declaration, importsMap))
                .collect(Collectors.toList());
    }

    /**
     * 处理单个字段类型的成员
     *
     * @param field 字段声明
     * @param importsMap  导包Map
     * @return FieldInfo
     */
    public static FieldInfo handleFieldMember(FieldDeclaration field, Map<String, String> importsMap) {
        List<Modifiers> modifiers = handleModifier(field.getModifiers());
        VariableDeclarator variable = field.getVariable(0);
        String fieldName = variable.getNameAsString();
        ClassInfo typeClass = TypeUtils.handleClassOrInterface(variable.getType(), importsMap);
        List<AnnotationInfo> annotationInfos = AnnotationExpressionUtils.handleAnnotationExpression(field.getAnnotations(), importsMap);
        return FieldInfo.of(fieldName, typeClass, modifiers, annotationInfos);
    }

    /**
     * 处理多个字段类型的成员
     *
     * @param declarations 字段声明
     * @param importsMap   导包Map
     * @return List<FieldInfo>
     */
    public static List<FieldInfo> handleFieldMember(List<FieldDeclaration> declarations, Map<String, String> importsMap) {
        return declarations
                .stream()
                .map(declaration -> handleFieldMember(declaration, importsMap))
                .collect(Collectors.toList());
    }


    /**
     * 处理单个参数类型的成员
     *
     * @param parameter  参数信息
     * @param importsMap 导包Map
     * @return ParamInfo
     */
    public static ParamInfo handleParameterMember(Parameter parameter, Map<String, String> importsMap) {
        Type parameterType = parameter.getType();
        ClassInfo classInfo = TypeUtils.handleClassOrInterface(parameterType, importsMap);
        List<ClassInfo> genericTypes = classInfo.getGenerics();
        classInfo.setGenerics(null);
        List<AnnotationInfo> annotationInfos = AnnotationExpressionUtils.handleAnnotationExpression(parameter.getAnnotations(), importsMap);
        return ParamInfo.of(parameter.getNameAsString(), annotationInfos, classInfo, genericTypes);
    }

    /**
     * 处理多个参数类型的成员
     *
     * @param parameters 参数信息
     * @param importsMap 导包Map
     * @return List<ParamInfo>
     */
    public static List<ParamInfo> handleParameterMember(NodeList<Parameter> parameters, Map<String, String> importsMap) {
        return parameters
                .stream()
                .map(parameter -> handleParameterMember(parameter, importsMap))
                .collect(Collectors.toList());
    }

    /**
     * 转换多个参数描述信息为JavaParser指定的参数类
     *
     * @param params 参数描述信息
     * @return NodeList<Parameter>
     */
    public static NodeList<Parameter> toParameterList(List<ParamInfo> params) {
        if (CollectionUtils.isEmpty(params)) {
            return new NodeList<>();
        }
        return params
                .stream()
                .map(MembersUtils::toParameter)
                .collect(Collectors.toCollection(NodeList::new));
    }

    /**
     * 转换单个参数描述信息为JavaParser指定的参数类
     *
     * @param param 参数描述信息
     * @return NodeList<Parameter>
     */
    public static Parameter toParameter(ParamInfo param) {

        // 处理参数注解
        List<AnnotationExpr> annotationExprList = AnnotationExpressionUtils.toAnnotationExprList(param.getAnnotations());
        // 处理参数类型
        ClassInfo paramClassInfo = param.getType();
        if (CollectionUtils.isEmpty(param.getGenerics())) {
            Parameter paramType = StaticJavaParser.parseParameter(paramClassInfo.getName() + " " + param.getName());
            return paramType
                    .setAnnotations(new NodeList<>(annotationExprList));
        } else {
            ClassOrInterfaceType paramType = StaticJavaParser.parseClassOrInterfaceType(paramClassInfo.getName());
            Type[] genericTypes = TypeUtils.toGenericTypeArr(param.getGenerics());
            paramType.setTypeArguments(genericTypes);
            return new Parameter(paramType, param.getName())
                    .setAnnotations(new NodeList<>(annotationExprList));
        }
    }


    /**
     * 收集类、方法、属性访问修饰符
     *
     * @param modifiers 访问修饰符集合
     * @return List<Modifier.Keyword>
     */
    public static List<Modifiers> handleModifier(NodeList<Modifier> modifiers) {
        Map<String, Modifiers> modifiersMap = Arrays
                .stream(Modifiers.values())
                .collect(Collectors.toMap(Modifiers::getValue,
                                          Function.identity(),
                                          (o, n) -> n));
        return modifiers
                .stream()
                .map(modifier -> modifier.getKeyword().name())
                .map(modifiersMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 转换为JavaParser识别的访问修饰符类
     *
     * @param modifiersList 访问修饰符
     * @return NodeList<Modifier>
     */
    public static NodeList<Modifier> toModifierList(List<Modifiers> modifiersList) {
        if (CollectionUtils.isEmpty(modifiersList)) {
            return new NodeList<>();
        }
        return modifiersList
                .stream()
                .map(modifiers -> Modifier.Keyword.valueOf(modifiers.name()))
                .map(Modifier::new)
                .collect(Collectors.toCollection(NodeList::new));
    }

    /**
     * 转换为JavaParser识别的修饰符关键字
     *
     * @param modifiersList 访问修饰符
     * @return Modifier.Keyword[]
     */
    public static Modifier.Keyword[] toModifierKeyWordArr(List<Modifiers> modifiersList) {
        if (CollectionUtils.isEmpty(modifiersList)) {
            return new Modifier.Keyword[0];
        }
        return toModifierList(modifiersList)
                .stream()
                .map(Modifier::getKeyword)
                .toArray(Modifier.Keyword[]::new);
    }

    public static VariableDeclarator toFieldVariableDec(FieldInfo fieldInfo) {
        Type type = TypeUtils.toFieldType(fieldInfo.getTypeClass());
        return new VariableDeclarator(type, fieldInfo.getId());
    }


    public static List<BodyDeclaration<?>> toMethodDeclaration(List<MethodInfo> methodInfos) {
        if (CollectionUtils.isEmpty(methodInfos)) {
            return Collections.emptyList();
        }
        return methodInfos
                .stream()
                .map(MembersUtils::toMethodDeclaration)
                .collect(Collectors.toList());
    }

    public static BodyDeclaration<?> toMethodDeclaration(MethodInfo methodInfo) {
        List<AnnotationExpr> annotationExprList = AnnotationExpressionUtils.toAnnotationExprList(methodInfo.getAnnotations());
        NodeList<Parameter> parameterList = MembersUtils.toParameterList(methodInfo.getParams());
        Type type = TypeUtils.toMethodReturnType(methodInfo.getReturnParam());
        NodeList<Modifier> modifierList = MembersUtils.toModifierList(methodInfo.getModifiers());
        MethodDeclaration methodDeclaration = new MethodDeclaration(modifierList, methodInfo.getName(), type, parameterList);
        methodDeclaration.setAnnotations(new NodeList<>(annotationExprList));
        MembersUtils.fillMethodBody(methodDeclaration, methodInfo);


        return methodDeclaration;
    }


    /**
     * 将枚举类内部信息，转换为枚举常量声明
     *
     * @param enumInfo 枚举类内部各枚举信息
     * @return EnumConstantDeclaration
     */
    public static EnumConstantDeclaration toEnumConstantDeclaration(EnumInfo enumInfo) {
        List<AnnotationExpr> annotationExprList = AnnotationExpressionUtils
                .toAnnotationExprList(enumInfo.getAnnotations());
        List<Expression> arguments = enumInfo
                .getArguments()
                .stream()
                .map(enumClazz -> ExpressionUtils
                        .getExpression(enumClazz.getType(),
                                       enumClazz.getValue()))
                .collect(Collectors.toList());
        List<BodyDeclaration<?>> methodDeclaration = MembersUtils.toMethodDeclaration(enumInfo.getMethods());
        return new EnumConstantDeclaration(new NodeList<>(annotationExprList),
                                           new SimpleName(enumInfo.getId()),
                                           new NodeList<>(arguments),
                                           new NodeList<>(methodDeclaration));
    }


    /**
     * 根据方法描述信息，填充方法定义内部的方法体内容
     *
     * @param methodDeclaration 方法定义
     * @param methodInfo        方法描述信息
     */
    public static void fillMethodBody(MethodDeclaration methodDeclaration, MethodInfo methodInfo) {
        String body = methodInfo.getBody();
        if (StringUtils.isNotBlank(body)) {
            methodDeclaration.setBody(StaticJavaParser.parseBlock(body));
        } else {
            boolean anyMatch = methodInfo
                    .getModifiers()
                    .stream()
                    .anyMatch(modifiers -> modifiers == Modifiers.ABSTRACT);
            if (anyMatch) {
                methodDeclaration.setBody(null);
            } else if (!StringUtils.equals(methodInfo.getReturnParam().getName(), "void")) {
                methodDeclaration.createBody()
                                 .addStatement("return null;");
            }
        }
    }

}
