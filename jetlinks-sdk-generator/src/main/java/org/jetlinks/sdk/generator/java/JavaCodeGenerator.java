package org.jetlinks.sdk.generator.java;

import com.github.javaparser.ast.body.MethodDeclaration;
import org.jetlinks.sdk.generator.java.base.AnnotationInfo;
import org.jetlinks.sdk.generator.java.base.ClassInfo;
import org.jetlinks.sdk.generator.java.base.FieldInfo;
import org.jetlinks.sdk.generator.java.base.MethodInfo;
import org.jetlinks.sdk.generator.java.base.enums.Modifiers;

import java.util.List;
import java.util.function.Consumer;

public interface JavaCodeGenerator {

    static JavaCodeGenerator create(String classPackage, String className) {
        return new JavaClassCodeGenerator(classPackage, className);
    }

    static JavaCodeGenerator createEnum(String classPackage, String className) {
        return new JavaEnumGenerator(classPackage, className);
    }

    static JavaCodeGenerator createInterface(String classPackage, String className) {
        return new JavaInterfaceGenerator(classPackage, className);
    }


    /**
     * 添加导包信息
     *
     * @param clazz 类导包路径
     * @return JavaCodeGenerator
     */
    JavaCodeGenerator addImport(String clazz);


    /**
     * 继承带泛型的父类
     *
     * @param superClass 父类信息
     * @return JavaCodeGenerator
     */
    JavaCodeGenerator extendsClass(ClassInfo superClass);

    /**
     * 实现带泛型的接口
     *
     * @param interfaces 接口描述
     * @return JavaCodeGenerator
     */
    JavaCodeGenerator implement(List<ClassInfo> interfaces);

    /**
     * 添加字段并同时添加字段注解信息
     *
     * @param fieldInfoList 字段信息
     * @return JavaCodeGenerator
     */
    JavaCodeGenerator addField(List<FieldInfo> fieldInfoList);

    /**
     * 添加类注解
     *
     * @param annotationInfoList@return JavaCodeGenerator
     */
    JavaCodeGenerator addClassAnnotation(List<AnnotationInfo> annotationInfoList);

    /**
     * 添加方法信息
     *
     * @param methodInfos 方法描述信息
     * @return JavaCodeGenerator
     */
    JavaCodeGenerator addMethod(List<MethodInfo> methodInfos);


    /**
     * 添加类泛型信息
     *
     * @param generics 类泛型描述
     * @return JavaCodeGenerator
     */
    JavaCodeGenerator addGeneric(List<ClassInfo> generics);

    /**
     * 添加类访问修饰符信息
     *
     * @param modifiersList 类访问修饰符描述
     * @return JavaCodeGenerator
     */
    JavaCodeGenerator addModifier(List<Modifiers> modifiersList);

    /**
     * 添加方法
     *
     * @param name       方法名称
     * @param customizer 方法定义信息填充
     * @return JavaCodeGenerator
     */
    JavaCodeGenerator addMethod(String name,
                                Consumer<MethodDeclaration> customizer);

    String generate(ClassInfo classInfo);

    String generate();
}
