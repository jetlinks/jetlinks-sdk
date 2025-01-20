package org.jetlinks.sdk.generator.java;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import org.apache.commons.lang3.StringUtils;
import org.jetlinks.sdk.generator.java.base.*;
import org.jetlinks.sdk.generator.java.base.enums.Modifiers;
import org.jetlinks.sdk.generator.java.utils.AnnotationExpressionUtils;
import org.jetlinks.sdk.generator.java.utils.ExpressionUtils;
import org.jetlinks.sdk.generator.java.utils.MembersUtils;
import org.jetlinks.sdk.generator.java.utils.TypeUtils;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultJavaCodeParser implements JavaCodeParser {

    @Override
    public ClassInfo parse(InputStream inputStream) {
        CompilationUnit cu = StaticJavaParser.parse(inputStream);
        Map<String, String> importsMap = TypeUtils.handleImports(cu.getImports());
        Set<String> importInfo = new HashSet<>(importsMap.values());
        cu.getPackageDeclaration()
          .ifPresent(packageDeclaration -> importsMap.put("classPackage", packageDeclaration.getNameAsString()));

        Optional<TypeDeclaration<?>> typeOptional = cu.getTypes().getFirst();
        if (typeOptional.isPresent()) {
            TypeDeclaration<?> typeDeclaration = typeOptional.get();
            return doParse(typeDeclaration, importsMap)
                    .withImportInfos(importInfo);

        }
        return ClassInfo.of();
    }

    /**
     * 解析类定义为类描述信息
     *
     * @param clazz      类定义
     * @param importsMap 导包Map
     */
    private void doParseClass(ClassOrInterfaceDeclaration clazz, ClassInfo classInfo, Map<String, String> importsMap) {
        List<ClassInfo> generics = clazz
                .getTypeParameters()
                .stream()
                .map(typeParameter -> TypeUtils.handleClassOrInterface(typeParameter, importsMap))
                .collect(Collectors.toList());

        //获取父类信息
        clazz.getExtendedTypes()
             .getFirst()
             .ifPresent(superType -> classInfo
                     .withSuperClass(TypeUtils.handleClassOrInterface(superType, importsMap)));

        // 获取接口信息
        List<ClassInfo> interfaceInfo = TypeUtils.handleClassOrInterface(clazz.getImplementedTypes(), importsMap);

        //填充类各部分信息
        classInfo
                .withInterfaces(interfaceInfo)
                .withGenerics(generics);
    }

    /**
     * 解析枚举类定义
     *
     * @param enumDeclaration 枚举类定义
     * @param classInfo       解析的枚举类信息
     * @param importsMap      导包Map
     */
    private void doParseEnum(EnumDeclaration enumDeclaration, ClassInfo classInfo, Map<String, String> importsMap) {
        // 处理枚举entries
        List<EnumInfo> enumInfos = enumDeclaration
                .getEntries()
                .stream()
                .map(entry -> {
                    EnumInfo enumInfo = EnumInfo.of(entry.getNameAsString());
                    List<ArgumentsInfo> expressionClassInfo = ExpressionUtils.getExpressionArgumentsInfos(entry.getArguments(), importsMap);
                    List<MethodInfo> methodInfos = entry
                            .getClassBody()
                            .stream()
                            .filter(BodyDeclaration::isMethodDeclaration) // todo 暂时忽略枚举非方法类型的定义
                            .map(BodyDeclaration::asMethodDeclaration)
                            .map(methodDec -> MembersUtils.handleMethodMember(methodDec, importsMap))
                            .collect(Collectors.toList());
                    return enumInfo
                            .withArguments(expressionClassInfo)
                            .withMethods(methodInfos);
                }).collect(Collectors.toList());

        // 获取接口信息
        List<ClassInfo> interfaceInfo = TypeUtils.handleClassOrInterface(enumDeclaration.getImplementedTypes(), importsMap);


        //填充类各部分信息
        classInfo
                .withInterfaces(interfaceInfo)
                .withFields(new ArrayList<>(enumInfos));
    }

    private ClassInfo doParse(TypeDeclaration<?> typeDeclaration, Map<String, String> importsMap) {
        String classPackage = importsMap.get("classPackage");
        String className = typeDeclaration.getNameAsString();
        if (StringUtils.isNotBlank(classPackage)) {
            classPackage = String.join(".", classPackage, className);
        }

        //构建类信息
        ClassInfo classInfo = ClassInfo.of(className, classPackage);

        //获取类上注解信息
        List<AnnotationInfo> annotationInfos = AnnotationExpressionUtils.handleAnnotationExpression(typeDeclaration.getAnnotations(), importsMap);


        // 获取字段、内部类、方法信息
        List<FieldInfo> fieldInfos = new ArrayList<>();
        List<MethodInfo> methodInfos = new ArrayList<>();

        Optional.ofNullable(typeDeclaration.getFields())
                .ifPresent(fields -> fieldInfos.addAll(MembersUtils.handleFieldMember(fields, importsMap)));

        Optional.ofNullable(typeDeclaration.getMethods())
                .ifPresent(methods -> methodInfos.addAll(MembersUtils.handleMethodMember(methods, importsMap)));

        Optional.ofNullable(typeDeclaration.getMembers())
                .ifPresent(members -> members
                        .stream()
                        .filter(BodyDeclaration::isClassOrInterfaceDeclaration)
                        .map(member -> doParse(member.asClassOrInterfaceDeclaration(), importsMap))
                        .peek(innerClass -> fieldInfos.add(FieldInfo.copyFrom(innerClass)))
                        .collect(Collectors.toList()));


        //获取类的访问修饰符
        List<Modifiers> modifiers = MembersUtils.handleModifier(typeDeclaration.getModifiers());

        if (typeDeclaration.isClassOrInterfaceDeclaration()) {
            doParseClass(typeDeclaration.asClassOrInterfaceDeclaration(), classInfo, importsMap);
        }
        if (typeDeclaration.isEnumDeclaration()) {
            doParseEnum(typeDeclaration.asEnumDeclaration(), classInfo, importsMap);
        }

        //填充类各部分信息
        return classInfo
                .withAnnotations(annotationInfos)
                .withFields(fieldInfos)
                .withMethods(methodInfos)
                .withModifiers(modifiers);
    }
}



