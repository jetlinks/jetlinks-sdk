package org.jetlinks.sdk.generator.java;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import org.jetlinks.sdk.generator.java.base.ClassInfo;
import org.jetlinks.sdk.generator.java.base.FieldInfo;
import org.jetlinks.sdk.generator.java.utils.AnnotationUtils;
import org.springframework.core.ResolvableType;

import java.util.function.Consumer;

class DefaultJavaGenerator implements JavaGenerator {

    final CompilationUnit cu;

    final ClassOrInterfaceDeclaration clazz;

    final String classSimpleName;

    DefaultJavaGenerator(String className) {
        cu = new CompilationUnit();
        if (className.contains(".")) {
            int lastIndex = className.lastIndexOf(".");
            cu.setPackageDeclaration(className.substring(0, lastIndex));
            className = classSimpleName = className.substring(lastIndex + 1);
        } else {
            classSimpleName = className;
        }
        clazz = cu.addClass(className);
    }

    @Override
    public JavaGenerator extendsClass(ResolvableType clazz) {
        addImport(clazz.toClass());
        return extendsClass(clazz.toString());
    }

    @Override
    public JavaGenerator extendsClass(String clazz) {
        this.clazz.addExtendedType(clazz);
        return this;
    }

    @Override
    public JavaGenerator implement(String clazz) {
        this.clazz.addImplementedType(clazz);
        return this;
    }

    @Override
    public JavaGenerator implement(ResolvableType clazz) {
        return implement(clazz.toString());
    }

    @Override
    public String getThis() {
        return classSimpleName;
    }

    @Override
    public JavaGenerator addMethod(String name, Consumer<MethodDeclaration> customizer) {
        MethodDeclaration methodDeclaration = clazz.addMethod(name);
        methodDeclaration.setPublic(true);
        customizer.accept(methodDeclaration);
        return this;
    }

    @Override
    public JavaGenerator comments(String... comments) {
        clazz.setJavadocComment(String.join("\n", comments));
        return this;
    }

    @Override
    public JavaGenerator addField(String type, String name, Modifier.Keyword... modifiers) {
        clazz.addField(type, name, modifiers);
        return this;
    }

    @Override
    public JavaGenerator extendsClass(String clazz, Type... types) {
        ClassOrInterfaceType genericParentType = new ClassOrInterfaceType(clazz);
        genericParentType.setTypeArguments(types);
        this.clazz.addExtendedType(genericParentType);
        return this;
    }

    @Override
    public JavaGenerator addFieldWithAnnotation(FieldInfo fieldInfo) {
        FieldDeclaration fieldDeclaration = clazz.addField(fieldInfo.getTypeClass(),
                                                           fieldInfo.getName(),
                                                           fieldInfo.getModifiers().toArray(new Modifier.Keyword[0]));
        AnnotationUtils
                .createAnnotation(fieldInfo.getAnnotations())
                .forEach(fieldDeclaration::addAnnotation);
        return this;
    }

    @Override
    public JavaGenerator addClassAnnotation(ClassInfo classInfo) {
        AnnotationUtils
                .createAnnotation(classInfo.getAnnotations())
                .forEach(clazz::addAnnotation);
        return this;
    }

    @Override
    public JavaGenerator addField(ResolvableType type, String name, Modifier.Keyword... modifiers) {
        return addField(type.toString(), name, modifiers);
    }

    @Override
    public JavaGenerator addImport(Class<?> clazz) {
        cu.addImport(clazz);
        return this;
    }

    @Override
    public JavaGenerator addImport(String clazz) {
        cu.addImport(clazz);
        return this;
    }

    @Override
    public String generate() {
        return cu.toString();
    }

    @Override
    public String toString() {
        return generate();
    }
}
