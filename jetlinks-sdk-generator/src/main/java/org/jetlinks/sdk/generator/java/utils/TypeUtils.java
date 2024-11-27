package org.jetlinks.sdk.generator.java.utils;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import org.apache.commons.lang3.StringUtils;
import org.jetlinks.sdk.generator.java.base.ClassInfo;
import org.jetlinks.sdk.generator.java.base.enums.Modifiers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 类、接口转换工具类
 */
public class TypeUtils {

    /**
     * 处理多个类或接口Type信息，同时处理包含的泛型
     *
     * @param types      类或接口Type信息
     * @param importsMap 导包Map
     * @return List<ClassInfo>
     */
    public static List<ClassInfo> handleClassOrInterface(NodeList<? extends Type> types, Map<String, String> importsMap) {
        return types
                .stream()
                .map(type -> handleClassOrInterface(type, importsMap))
                .collect(Collectors.toList());
    }

    /**
     * 处理单个类或接口Type信息，同时处理包含的泛型
     *
     * @param type       类或接口Type信息
     * @param importsMap 导包Map
     * @return ClassInfo
     */
    public static ClassInfo handleClassOrInterface(Type type, Map<String, String> importsMap) {
        if (type.isPrimitiveType()) {
            return ClassInfo.of(type.asPrimitiveType().asString());
        } else if (type.isVoidType()) {
            return ClassInfo.of(type.asVoidType().asString());
        } else {
            ClassInfo classInfo = toClassInfo(type, importsMap);
            type.asClassOrInterfaceType()
                .getTypeArguments()
                .ifPresent(types -> classInfo.withGenerics(toClassInfo(types, importsMap)));
            return classInfo;
        }
    }

    /**
     * 转换单个类或接口Type为类或接口描述信息
     *
     * @param type       类或接口Type信息
     * @param importsMap 导包Map
     * @return ClassInfo
     */
    public static ClassInfo toClassInfo(Type type, Map<String, String> importsMap) {
        ClassOrInterfaceType classType = type.asClassOrInterfaceType();
        String className = classType.getNameAsString();
        String classPackage = importsMap.get(className);
        if (!classType.isBoxedType() && Objects.isNull(importsMap.get(className))) {
            if (!StringUtils.equals(className, "Object")
                    && !StringUtils.equals(className, "String")) {
                String baseClassPackage = importsMap.get("classPackage");
                if (StringUtils.isNotBlank(baseClassPackage)) {
                    classPackage = String.join(".", baseClassPackage, className);
                }
            }
        }
        return ClassInfo.of(className, classPackage);
    }

    /**
     * 转换多个类或接口Type为类或接口描述信息
     *
     * @param types      类或接口Type信息
     * @param importsMap 导包Map
     * @return ClassInfo
     */
    public static List<ClassInfo> toClassInfo(NodeList<Type> types, Map<String, String> importsMap) {
        return types
                .stream()
                .map(type -> toClassInfo(type, importsMap))
                .collect(Collectors.toList());
    }

    /**
     * 构建导包Map
     *
     * @param imports 导包集合信息
     * @return Map<String, String>
     */
    public static Map<String, String> handleImports(NodeList<ImportDeclaration> imports) {
        return imports
                .stream()
                .map(ImportDeclaration::getName)
                .collect(Collectors.toMap(Name::getIdentifier, Name::asString, (o, n) -> n));
    }

    /**
     * 收集访问修饰符
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
}
