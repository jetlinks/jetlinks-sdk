package org.jetlinks.sdk.generator.java.utils;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.type.Type;
import org.jetlinks.sdk.generator.java.base.*;
import org.jetlinks.sdk.generator.java.base.enums.Modifiers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 成员（类中字段、方法、参数等）转换工具类
 */
public class MembersUtils {

    /**
     * 处理单个方法类型的成员
     *
     * @param declaration 方法声明
     * @param importsMap  导包Map
     * @return MethodInfo
     */
    public static MethodInfo handleMethodMember(BodyDeclaration<?> declaration, Map<String, String> importsMap) {
        MethodDeclaration method = declaration.asMethodDeclaration();
        List<Modifiers> modifiers = collectModifier(method.getModifiers());
        List<AnnotationInfo> annotationInfos = AnnotationExpressionUtils.handleAnnotationExpression(method.getAnnotations(), importsMap);
        List<ParamInfo> paramInfos = handleParameterMember(method.getParameters(), importsMap);
        ClassInfo returnType = TypeUtils.handleClassOrInterface(method.getType(), importsMap);
        return MethodInfo.of(method.getNameAsString(), annotationInfos, paramInfos, returnType, modifiers);
    }

    /**
     * 处理多个方法类型的成员
     *
     * @param declarations 方法声明
     * @param importsMap   导包Map
     * @return List<MethodInfo>
     */
    public static List<MethodInfo> handleMethodMember(List<BodyDeclaration<?>> declarations, Map<String, String> importsMap) {
        return declarations
                .stream()
                .map(declaration -> handleMethodMember(declaration, importsMap))
                .collect(Collectors.toList());
    }

    /**
     * 处理单个字段类型的成员
     *
     * @param declaration 字段声明
     * @param importsMap  导包Map
     * @return FieldInfo
     */
    public static FieldInfo handleFieldMember(BodyDeclaration<?> declaration, Map<String, String> importsMap) {
        FieldDeclaration field = declaration.asFieldDeclaration();
        List<Modifiers> modifiers = collectModifier(field.getModifiers());
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
    public static List<FieldInfo> handleFieldMember(List<BodyDeclaration<?>> declarations, Map<String, String> importsMap) {
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
     * 收集类、方法、属性访问修饰符
     *
     * @param modifiers 访问修饰符集合
     * @return List<Modifier.Keyword>
     */
    public static List<Modifiers> collectModifier(NodeList<Modifier> modifiers) {
        Map<String, Modifiers> modifiersMap = Arrays
                .stream(Modifiers.values())
                .collect(Collectors.toMap(Modifiers::name, Function.identity()));
        return modifiers
                .stream()
                .map(modifier -> modifier.getKeyword().name())
                .map(modifiersMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
