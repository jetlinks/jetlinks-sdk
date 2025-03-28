package org.jetlinks.sdk.generator.java;


import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import org.jetlinks.sdk.generator.java.base.EnumInfo;
import org.jetlinks.sdk.generator.java.utils.MembersUtils;

public class JavaEnumGenerator extends AbstractJavaCodeGenerator {

    public JavaEnumGenerator(String clazzPackage, String className) {
        this.cu = new CompilationUnit(clazzPackage);
        clazz = cu.addEnum(className);
    }


    @Override
    protected void addImplementedType(ClassOrInterfaceType interfaceType) {
        this.clazz.asEnumDeclaration().addImplementedType(interfaceType);
    }

    @Override
    protected void handleEnumInfo(EnumInfo enumInfo) {
        EnumConstantDeclaration enumConstantDeclaration = MembersUtils.toEnumConstantDeclaration(enumInfo);
        clazz.asEnumDeclaration().addEntry(enumConstantDeclaration);
    }
}
