package org.jetlinks.sdk.generator.java.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.type.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetlinks.sdk.generator.java.base.ClassInfo;

import java.util.*;
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
        } else if (type.isArrayType()) {
            Type componentType = type.asArrayType().getComponentType();
            return ClassInfo.of("Array")
                            .withGenerics(Collections.singletonList(handleClassOrInterface(componentType, importsMap)));
        } else if (type.isWildcardType()) {
            WildcardType wildcardType = type.asWildcardType();
            Optional<ReferenceType> extendedType = wildcardType.getExtendedType();
            Optional<ReferenceType> superType = wildcardType.getSuperType();
            return extendedType
                    .map(referenceType -> toClassInfo(referenceType, importsMap))
                    .orElseGet(() -> superType
                            .map(referenceType -> toClassInfo(referenceType, importsMap))
                            .orElseGet(() -> ClassInfo.of(type.asWildcardType().asString())));

        } else if (type.isTypeParameter()) {
            String paramName = type.asTypeParameter().getNameAsString();
            return ClassInfo.of(paramName, importsMap.get(paramName));
        } else {
            ClassInfo classInfo = toClassInfo(type, importsMap);
            type.asClassOrInterfaceType()
                .getTypeArguments()
                .ifPresent(types -> classInfo.withGenerics(handleClassOrInterface(types, importsMap)));
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
     * 转换泛型为JavaParser指定的类型
     *
     * @param generics      带泛型的类描述
     * @return ype[]
     */
    public static Type[] toGenericTypeArr(List<ClassInfo> generics) {
        if (CollectionUtils.isEmpty(generics)) {
            return new Type[0];
        }
        return generics
                .stream()
                .map(clazz -> {
                    Type type = StaticJavaParser.parseType(clazz.getName());
                    if (CollectionUtils.isNotEmpty(clazz.getGenerics())
                            && type.isClassOrInterfaceType()) {
                        Type[] genericTypeArr = toGenericTypeArr(clazz.getGenerics());
                        ClassOrInterfaceType classOrInterfaceType = type.asClassOrInterfaceType();
                        classOrInterfaceType.setTypeArguments(genericTypeArr);
                        return classOrInterfaceType;
                    }
                    return type;
                })
                .toArray(Type[]::new);
    }

    /**
     * 转换泛型为JavaParser指定的类型
     *
     * @param generics      带泛型的类描述
     * @return ype[]
     */
    public static NodeList<TypeParameter> toTypeParameterArr(List<ClassInfo> generics) {
        if (CollectionUtils.isEmpty(generics)) {
            return new NodeList<>();
        }
        return generics
                .stream()
                .map(clazz -> StaticJavaParser.parseTypeParameter(clazz.getName()))
                .collect(Collectors.toCollection(NodeList::new));
    }

    /**
     * 转换方法返回值描述为JavaParser指定的类型
     *
     * @param returnParam 方法返回值描述
     * @return Type
     */
    public static Type toMethodReturnType(ClassInfo returnParam) {
        return toFieldType(returnParam);
    }


    public static Type toFieldType(ClassInfo fieldClass) {
        String fieldClassName = fieldClass.getName();
        if (StringUtils.equals("Array", fieldClassName)) {
            ClassInfo generic = fieldClass.getGenerics().get(0);
            return new ArrayType(StaticJavaParser.parseType(generic.getName()));
        }

        Optional<PrimitiveType.Primitive> primitive = PrimitiveType
                .Primitive
                .byTypeName(fieldClassName);
        if (primitive.isPresent()) {
            return new PrimitiveType(primitive.get());
        }

        if (StringUtils.equals("void", fieldClassName)) {
            return new VoidType();
        }

        ClassOrInterfaceType returnParamType = StaticJavaParser.parseClassOrInterfaceType(fieldClassName);

        Type[] genericTypes = TypeUtils.toGenericTypeArr(fieldClass.getGenerics());
        if (ArrayUtils.isNotEmpty(genericTypes)) {
            returnParamType.setTypeArguments(genericTypes);
        }
        return returnParamType;
    }


}
