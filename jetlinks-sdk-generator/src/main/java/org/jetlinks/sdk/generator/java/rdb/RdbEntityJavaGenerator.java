package org.jetlinks.sdk.generator.java.rdb;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.Type;
import org.jetlinks.sdk.generator.java.JavaGenerator;

import java.util.List;

public interface RdbEntityJavaGenerator extends JavaGenerator {

    static RdbEntityJavaGenerator create(String packageName, String className) {
        return new DefaultRdbEntityJavaGenerator(packageName, className);
    }

    RdbEntityJavaGenerator extendsClass(String clazz, Type... types);

    /**
     * 添加字段并同时添加字段注解信息
     *
     * @param type        字段类型
     * @param name        字段名称
     * @param annotations 注解信息
     * @param modifiers   字段修饰符
     * @return RdbEntityJavaGenerator
     */
    RdbEntityJavaGenerator addFieldWithAnnotation(String type, String name, List<AnnotationExpr> annotations, Modifier.Keyword... modifiers);

    /**
     * 添加类注解
     *
     * @param annotation 注解表达式
     * @return RdbEntityJavaGenerator
     */
    RdbEntityJavaGenerator addClassAnnotation(AnnotationExpr annotation);

}
