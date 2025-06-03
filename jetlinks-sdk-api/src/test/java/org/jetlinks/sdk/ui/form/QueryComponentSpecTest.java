package org.jetlinks.sdk.ui.form;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetlinks.core.utils.MetadataUtils;
import org.jetlinks.sdk.server.ui.field.annotation.field.form.QueryComponent;
import org.jetlinks.sdk.server.ui.field.annotation.field.form.QueryComponentSpec;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryComponentSpecTest {

    @QueryComponent(fields = Person.class)
    public void annotatedMethod() {
    }


    @Test
    @SneakyThrows
    void test() {
        Method method = QueryComponentSpecTest.class.getMethod("annotatedMethod");
        QueryComponent annotation = method.getAnnotation(QueryComponent.class);
        QueryComponentSpec queryComponentSpec = new QueryComponentSpec();
        queryComponentSpec.setFields(Collections.emptyList());

        Map<String, Object> expands = queryComponentSpec.toExpands();
        Map<String, Object> annotationExpands = MetadataUtils.parseExpands(annotation);

        assertEquals(expands, annotationExpands, "解析的扩展属性和注解上的扩展属性不一致");

    }

    @Getter
    @Setter
    public static class Person {
        private String name;
        private int age;
    }
}
