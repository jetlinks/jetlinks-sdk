package org.jetlinks.sdk.generator.java.rdb;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import org.jetlinks.sdk.generator.java.JavaGenerator;
import org.springframework.core.ResolvableType;

import java.util.List;
import java.util.function.Consumer;

public class DefaultRdbEntityJavaGenerator implements RdbEntityJavaGenerator {

    final CompilationUnit cu;

    final ClassOrInterfaceDeclaration clazz;

    final String classPackage;
    final String classSimpleName;

    DefaultRdbEntityJavaGenerator(String packageName, String className) {
        cu = new CompilationUnit();
        classPackage = packageName;
        classSimpleName = className;
        cu.setPackageDeclaration(classPackage);
        clazz = cu.addClass(classSimpleName);
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
    public RdbEntityJavaGenerator extendsClass(String clazz, Type... types) {
        ClassOrInterfaceType genericParentType = new ClassOrInterfaceType(clazz);
        genericParentType.setTypeArguments(types);
        this.clazz.addExtendedType(genericParentType);
        return this;
    }

    @Override
    public RdbEntityJavaGenerator addFieldWithAnnotation(String type, String name, List<AnnotationExpr> annotations, Modifier.Keyword... modifiers) {
        FieldDeclaration fieldDeclaration = clazz.addField(type, name, modifiers);
        annotations.forEach(fieldDeclaration::addAnnotation);
        return this;
    }

    @Override
    public RdbEntityJavaGenerator addClassAnnotation(AnnotationExpr annotation) {
        clazz.addAnnotation(annotation);
        return this;
    }

    @Override
    public String generate() {
        return cu.toString();
    }
}
