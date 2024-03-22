package org.jetlinks.sdk.generator.java;

import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;

import java.io.Serializable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JavaGeneratorTest {


    @Test
    void test() {
        String java = JavaGenerator
            .create("org.jetlinks.test.Test")
            .implement(ResolvableType.forClass(Serializable.class))
            .comments("测试类",
                      "@author zhouhao")
            .addImport(List.class)
            .addMethod("setName", method -> {

                method.setType("Test");

                method
                    .addParameter(String.class, "name")
                    .createBody()
                    .addStatement("this.name=name;")
                    .addStatement("return this;")
                ;
            }).generate();

        assertNotNull(java);
        System.out.println(java);

    }
}