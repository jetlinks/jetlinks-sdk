package org.jetlinks.sdk.server.utils;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class ObjectMappersTest {

    @Test
    void testParseJsonStreamField_SimpleArrayField() {
        // 测试简单数组字段的流式解析
        String jsonData = "{\"data\":[1,2,3,4],\"other\":\"value\"}";

        ObjectMappers
            .parseJsonStreamField(
                Flux.just(DefaultDataBufferFactory.sharedInstance.wrap(jsonData.getBytes())),
                "data",
                Integer.class
            )
            .doOnNext(value -> System.out.println("提取到的值: " + value))
            .as(StepVerifier::create)
            .expectNext(1, 2, 3, 4)
            .verifyComplete();
    }

    @Test
    void testParseJsonStreamField_SimpleStringField() {
        // 测试简单字符串字段的流式解析
        String jsonData = "{\"name\":\"test\",\"data\":[1,2,3]}";

        ObjectMappers
            .parseJsonStreamField(
                Flux.just(DefaultDataBufferFactory.sharedInstance.wrap(jsonData.getBytes())),
                "name",
                String.class
            )
            .doOnNext(value -> System.out.println("提取到的字符串: " + value))
            .as(StepVerifier::create)
            .expectNext("test")
            .verifyComplete();
    }

    @Test
    void testParseJsonStreamField_EmptyArray() {
        // 测试空数组字段的处理
        String jsonData = "{\"data\":[],\"other\":\"value\"}";

        ObjectMappers
            .parseJsonStreamField(
                Flux.just(DefaultDataBufferFactory.sharedInstance.wrap(jsonData.getBytes())),
                "data",
                Integer.class
            )
            .as(StepVerifier::create)
            .verifyComplete();
    }

    @Test
    void testParseJsonStreamField_NestedObjectField() {
        // 测试嵌套对象字段的流式解析
        String jsonData = "{\"result\":{\"items\":[\"item1\",\"item2\",\"item3\"]},\"status\":\"success\"}";

        ObjectMappers
            .parseJsonStreamField(
                Flux.just(DefaultDataBufferFactory.sharedInstance.wrap(jsonData.getBytes())),
                "result.items",
                String.class
            )
            .doOnNext(value -> System.out.println("提取到的嵌套值: " + value))
            .as(StepVerifier::create)
            .expectNext("item1", "item2", "item3")
            .verifyComplete();
    }

    @Test
    void testParseJsonStreamField_DeepNestedField() {
        // 测试深层嵌套字段的解析
        String jsonData = "{\"response\":{\"data\":{\"list\":[100,200,300]}},\"meta\":{\"total\":3}}";

        ObjectMappers
            .parseJsonStreamField(
                Flux.just(DefaultDataBufferFactory.sharedInstance.wrap(jsonData.getBytes())),
                "response.data.list",
                Integer.class
            )
            .doOnNext(value -> System.out.println("提取到的深层嵌套值: " + value))
            .as(StepVerifier::create)
            .expectNext(100, 200, 300)
            .verifyComplete();
    }

    @Test
    void testParseJsonStreamField_NestedStringField() {
        // 测试嵌套字符串字段的解析
        String jsonData = "{\"user\":{\"info\":{\"name\":\"张三\"}},\"timestamp\":1234567890}";

        ObjectMappers
            .parseJsonStreamField(
                Flux.just(DefaultDataBufferFactory.sharedInstance.wrap(jsonData.getBytes())),
                "user.info.name",
                String.class
            )
            .doOnNext(value -> System.out.println("提取到的嵌套姓名: " + value))
            .as(StepVerifier::create)
            .expectNext("张三")
            .verifyComplete();
    }

    @Test
    void testParseJsonStreamField_ComplexObjectArray() {
        // 测试复杂对象数组的流式解析
        String jsonData = "{" +
            "\"products\": [" +
                "{\"id\": 1, \"name\": \"产品A\", \"price\": 100.0}," +
                "{\"id\": 2, \"name\": \"产品B\", \"price\": 200.0}" +
            "]," +
            "\"total\": 2" +
        "}";

        ObjectMappers
            .parseJsonStreamField(
                Flux.just(DefaultDataBufferFactory.sharedInstance.wrap(jsonData.getBytes())),
                "products",
                Object.class
            )
            .doOnNext(product -> System.out.println("提取到的产品对象: " + product))
            .as(StepVerifier::create)
            .expectNextCount(2)
            .verifyComplete();
    }

    @Test
    void testParseJsonStreamField_MixedDataTypes() {
        // 测试混合数据类型的数组
        String jsonData = "{\"values\":[1,\"text\",true,null,3.14],\"type\":\"mixed\"}";

        ObjectMappers
            .parseJsonStreamField(
                Flux.just(DefaultDataBufferFactory.sharedInstance.wrap(jsonData.getBytes())),
                "values",
                Object.class
            )
            .doOnNext(value -> System.out.println("提取到的混合类型值: " + value + " (类型: " + 
                (value != null ? value.getClass().getSimpleName() : "null") + ")"))
            .as(StepVerifier::create)
            .expectNextCount(4) // null值被跳过，实际只有4个值
            .verifyComplete();
    }

    @Test
    void testParseJsonStreamField_LargeJsonStructure() {
        // 测试大型 JSON 结构中的字段提取
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"metadata\":{\"version\":\"2.0\",\"timestamp\":1234567890},");
        jsonBuilder.append("\"data\":[");

        for (int i = 0; i < 100; i++) {
            if (i > 0) jsonBuilder.append(",");
            jsonBuilder.append(i);
        }

        jsonBuilder.append("],\"summary\":{\"count\":100,\"status\":\"complete\"}}");

        String jsonData = jsonBuilder.toString();

        ObjectMappers
            .parseJsonStreamField(
                Flux.just(DefaultDataBufferFactory.sharedInstance.wrap(jsonData.getBytes())),
                "data",
                Integer.class
            )
            .doOnNext(value -> {
                if (value % 20 == 0) {
                    System.out.println("处理大型数组中的值: " + value);
                }
            })
            .as(StepVerifier::create)
            .expectNextCount(100)
            .verifyComplete();
    }

    @Test
    void testParseJsonStreamField_NonExistentField() {
        // 测试访问不存在的字段
        String jsonData = "{\"data\":[1,2,3],\"other\":\"value\"}";

        ObjectMappers
            .parseJsonStreamField(
                Flux.just(DefaultDataBufferFactory.sharedInstance.wrap(jsonData.getBytes())),
                "nonexistent",
                String.class
            )
            .as(StepVerifier::create)
            .verifyComplete(); // 应该没有任何元素发出
    }

    @Test
    void testParseJsonStreamField_InvalidJsonData() {
        // 测试无效的 JSON 数据
        String invalidJsonData = "{\"data\":[1,2,3"; // 缺少闭合括号

        ObjectMappers
            .parseJsonStreamField(
                Flux.just(DefaultDataBufferFactory.sharedInstance.wrap(invalidJsonData.getBytes())),
                "data",
                Integer.class
            )
            .as(StepVerifier::create)
            .expectNext(1, 2, 3) // 实际上可能能解析部分数据
            .expectError() // 然后出现错误
            .verify();
    }

    @Test
    void testParseJsonStreamField_InvalidNestedPath() {
        // 测试无效的嵌套路径
        String jsonData = "{\"user\":{\"name\":\"test\"},\"data\":[1,2,3]}";

        ObjectMappers
            .parseJsonStreamField(
                Flux.just(DefaultDataBufferFactory.sharedInstance.wrap(jsonData.getBytes())),
                "user.nonexistent.field",
                String.class
            )
            .as(StepVerifier::create)
            .verifyComplete(); // 路径不存在，不应该发出任何元素
    }

    @Test
    void testParseJsonStreamField_TypeMismatch() {
        // 测试类型不匹配的情况
        String jsonData = "{\"data\":[\"text1\",\"text2\"],\"number\":123}";

        // 尝试将字符串数组解析为 Integer 类型，错误的项会被跳过
        ObjectMappers
            .parseJsonStreamField(
                Flux.just(DefaultDataBufferFactory.sharedInstance.wrap(jsonData.getBytes())),
                "data",
                Integer.class
            )
            .as(StepVerifier::create)
            .verifyComplete(); // 由于类型不匹配，所有项都被跳过，应该直接完成
    }

}