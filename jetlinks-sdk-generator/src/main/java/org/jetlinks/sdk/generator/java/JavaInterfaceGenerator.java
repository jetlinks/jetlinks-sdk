package org.jetlinks.sdk.generator.java;

import com.github.javaparser.ast.CompilationUnit;

public class JavaInterfaceGenerator extends JavaClassCodeGenerator {

    public JavaInterfaceGenerator(String clazzPackage, String className) {
        super(clazzPackage, className);
        this.cu = new CompilationUnit(clazzPackage);
        clazz = cu.addInterface(className);
    }
}
