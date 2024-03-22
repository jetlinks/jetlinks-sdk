package org.jetlinks.sdk.generator.java;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.MethodDeclaration;
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


    String generate();
}
