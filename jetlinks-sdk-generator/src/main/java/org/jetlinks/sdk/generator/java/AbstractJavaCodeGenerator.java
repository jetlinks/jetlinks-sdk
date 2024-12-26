package org.jetlinks.sdk.generator.java;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.jetlinks.sdk.generator.java.base.*;
import org.jetlinks.sdk.generator.java.base.enums.Modifiers;
import org.jetlinks.sdk.generator.java.utils.AnnotationExpressionUtils;
import org.jetlinks.sdk.generator.java.utils.MembersUtils;
import org.jetlinks.sdk.generator.java.utils.TypeUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;


public abstract class AbstractJavaCodeGenerator implements JavaCodeGenerator {

    protected CompilationUnit cu;

    protected TypeDeclaration<?> clazz;


    @Override
    public JavaCodeGenerator addImport(String clazz) {
        cu.addImport(clazz);
        return this;
    }

    @Override
    public JavaCodeGenerator extendsClass(ClassInfo superClass) {
        if (Objects.isNull(superClass)) {
            return this;
        }
        ClassOrInterfaceType superClazz = StaticJavaParser.parseClassOrInterfaceType(superClass.getName());
        Type[] types = TypeUtils.toGenericTypeArr(superClass.getGenerics());
        if (ArrayUtils.isNotEmpty(types)) {
            superClazz.setTypeArguments(types);
        }
        addExtendedType(superClazz);
        return this;
    }


    @Override
    public JavaCodeGenerator addField(List<FieldInfo> fieldInfoList) {
        if (CollectionUtils.isEmpty(fieldInfoList)) {
            return this;
        }

        for (FieldInfo fieldInfo : fieldInfoList) {
            if (fieldInfo instanceof EnumInfo) {
                handleEnumInfo(((EnumInfo) fieldInfo));
            } else {
                ClassInfo typeClass = fieldInfo.getTypeClass();
                Modifier.Keyword[] keywords = MembersUtils.toModifierKeyWordArr(fieldInfo.getModifiers());
                FieldDeclaration fieldDeclaration = clazz.addField(typeClass.getName(), fieldInfo.getId(), keywords);
                VariableDeclarator fieldVariableDec = MembersUtils.toFieldVariableDec(fieldInfo);
                fieldDeclaration.setVariables(new NodeList<>(fieldVariableDec));
                AnnotationExpressionUtils
                        .toAnnotationExprList(fieldInfo.getAnnotations())
                        .forEach(fieldDeclaration::addAnnotation);
            }

        }


        return this;
    }


    @Override
    public JavaCodeGenerator addClassAnnotation(List<AnnotationInfo> annotationInfoList) {
        if (CollectionUtils.isEmpty(annotationInfoList)) {
            return this;
        }
        AnnotationExpressionUtils
                .toAnnotationExprList(annotationInfoList)
                .forEach(clazz::addAnnotation);
        return this;
    }

    @Override
    public JavaCodeGenerator implement(List<ClassInfo> interfaces) {
        if (CollectionUtils.isEmpty(interfaces)) {
            return this;
        }
        for (ClassInfo anInterface : interfaces) {
            Type[] types = TypeUtils.toGenericTypeArr(anInterface.getGenerics());
            ClassOrInterfaceType interfaceType = StaticJavaParser.parseClassOrInterfaceType(anInterface.getName());
            if (ArrayUtils.isNotEmpty(types)) {
                interfaceType.setTypeArguments(types);
            }
            addImplementedType(interfaceType);
        }

        return this;
    }


    @Override
    public JavaCodeGenerator addMethod(List<MethodInfo> methodInfos) {
        if (CollectionUtils.isEmpty(methodInfos)) {
            return this;
        }
        for (MethodInfo methodInfo : methodInfos) {
            this.addMethod(methodInfo.getName(), method -> {
                method.setModifiers(MembersUtils.toModifierList(methodInfo.getModifiers()))
                      .setParameters(MembersUtils.toParameterList(methodInfo.getParams()))
                      .setType(TypeUtils.toMethodReturnType(methodInfo.getReturnParam()));
                AnnotationExpressionUtils
                        .toAnnotationExprList(methodInfo.getAnnotations())
                        .forEach(method::addAnnotation);
                MembersUtils.fillMethodBody(method, methodInfo);


            });
        }
        return this;
    }

    @Override
    public JavaCodeGenerator addGeneric(List<ClassInfo> generics) {
        if (CollectionUtils.isEmpty(generics)) {
            return this;
        }
        setTypeParameters(TypeUtils.toTypeParameterArr(generics));
        return this;
    }


    @Override
    public JavaCodeGenerator addModifier(List<Modifiers> modifiersList) {
        if (CollectionUtils.isEmpty(modifiersList)) {
            return this;
        }
        this.clazz.setModifiers(MembersUtils.toModifierList(modifiersList));
        return this;
    }

    @Override
    public JavaCodeGenerator addMethod(String name, Consumer<MethodDeclaration> customizer) {
        MethodDeclaration methodDeclaration = clazz.addMethod(name);
        methodDeclaration.setPublic(true);
        customizer.accept(methodDeclaration);
        return this;
    }

    @Override
    public String generate(ClassInfo classInfo) {
        Optional.ofNullable(classInfo.getImportInfos())
                .ifPresent(importPackages -> classInfo
                        .getImportInfos()
                        .forEach(this::addImport));
        return this
                .addClassAnnotation(classInfo.getAnnotations())       //处理注解
                .extendsClass(classInfo.getSuperClass()) //处理父类信息，泛型也需处理
                .implement(classInfo.getInterfaces())//处理实现的接口，同时处理泛型
                .addMethod(classInfo.getMethods())//处理方法
                .addField(classInfo.getFields())// 处理字段信息
                .addGeneric(classInfo.getGenerics()) //处理泛型
                .addModifier(classInfo.getModifiers())  //处理类访问修饰符
                .generate();
    }

    @Override
    public String generate() {
        return cu.toString();
    }

    protected void handleEnumInfo(EnumInfo enumInfo) {
    }

    protected void addImplementedType(ClassOrInterfaceType interfaceType) {
    }

    protected void setTypeParameters(NodeList<TypeParameter> parameters) {
    }

    protected void addExtendedType(ClassOrInterfaceType superClazz) {
    }
}
