package org.jetlinks.sdk.server.ui;

import lombok.SneakyThrows;
import org.jetlinks.core.utils.MetadataUtils;
import org.jetlinks.sdk.server.ui.field.annotation.field.form.QueryComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class QueryComponentTest {


    @QueryComponent(property = "test", fields = MyEntity.class)
    void method() {

    }


    static class MyEntity{

    }
    @Test
    @SneakyThrows
    void test() {

        Map<String,Object> expands=   MetadataUtils.parseExpands(QueryComponentTest.class.getDeclaredMethod("method")
                                                                                         .getAnnotations());
        System.out.println(expands);

        Map<String,Object> formComponent = (Map<String, Object>) expands.get("formComponent");
        Assertions.assertNotNull(formComponent);
        Assertions.assertNotNull(formComponent.get("property"));
        Assertions.assertNotNull(formComponent.get("fields"));
    }
}
