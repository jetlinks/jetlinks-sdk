package org.jetlinks.sdk.generator.java;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.Type;
import org.jetlinks.sdk.generator.java.base.ClassInfo;
import org.jetlinks.sdk.generator.java.base.FieldInfo;
import org.springframework.core.ResolvableType;

import java.util.function.Consumer;

/**
 * Java代码生成器,用于构造java类.
 *
 * @author zhouhao
 * @since 1.0
 */
public interface JavaGenerator {

    static JavaGenerator create(String className) {
        return new DefaultJavaGenerator(className);
    }

    JavaGenerator extendsClass(ResolvableType clazz);

    JavaGenerator extendsClass(String clazz);

    JavaGenerator implement(ResolvableType clazz);

    JavaGenerator implement(String clazz);

    String getThis();

    JavaGenerator addImport(String clazz);

    JavaGenerator addImport(Class<?> clazz);

    JavaGenerator addMethod(String name,
                            Consumer<MethodDeclaration> customizer);

    JavaGenerator comments(String... comments);

    JavaGenerator addField(ResolvableType type, String name, Modifier.Keyword... modifiers);

    JavaGenerator addField(String type, String name, Modifier.Keyword... modifiers);

    /**
     * 继承带泛型的父类
     *
     * @param clazz 父类名称
     * @param types 泛型
     * @return JavaGenerator
     */
    JavaGenerator extendsClass(String clazz, Type... types);

    /**
     * 添加字段并同时添加字段注解信息
     * @param fieldInfo 字段信息
     * @return RdbEntityJavaGenerator
     */
    JavaGenerator addFieldWithAnnotation(FieldInfo fieldInfo);

    /**
     * 添加类注解
     *
     * @param classInfo 类信息
     * @return RdbEntityJavaGenerator
     */
    JavaGenerator addClassAnnotation(ClassInfo classInfo);


    String generate();
}
