package org.jetlinks.sdk.generator.java;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.TypeParameter;

public class JavaClassCodeGenerator extends AbstractJavaCodeGenerator {

    public JavaClassCodeGenerator(String clazzPackage, String className) {
        this.cu = new CompilationUnit(clazzPackage);
        clazz = cu.addClass(className);
    }

    @Override
    protected void addExtendedType(ClassOrInterfaceType superClazz) {
        this.clazz
                .asClassOrInterfaceDeclaration()
                .addExtendedType(superClazz);
    }

    @Override
    protected void addImplementedType(ClassOrInterfaceType interfaceType) {
        this.clazz
                .asClassOrInterfaceDeclaration()
                .addImplementedType(interfaceType);
    }

    @Override
    protected void setTypeParameters(NodeList<TypeParameter> parameters) {
        this.clazz
                .asClassOrInterfaceDeclaration()
                .setTypeParameters(parameters);
    }
}
