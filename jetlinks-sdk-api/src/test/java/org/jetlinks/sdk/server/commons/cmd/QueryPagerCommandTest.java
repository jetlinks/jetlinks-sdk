package org.jetlinks.sdk.server.commons.cmd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryPagerCommandTest {


    @Test
    void test(){
        FunctionMetadata metadata = QueryPagerCommand.metadata(TestEntity.class);
        assertNotNull(metadata);

        System.out.println(JSON.toJSONString(metadata.toJson(), SerializerFeature.PrettyFormat));

    }

    @Getter
    @Setter
    @Schema(title = "测试实体类")
    public static class TestEntity {

        @Schema
        private String id;
    }
}