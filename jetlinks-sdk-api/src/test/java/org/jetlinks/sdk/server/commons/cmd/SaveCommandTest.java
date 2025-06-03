package org.jetlinks.sdk.server.commons.cmd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;

import static org.junit.jupiter.api.Assertions.*;

class SaveCommandTest {


    @Test
    void testMetadata() {

        FunctionMetadata metadata = SaveCommand.metadata(TestEntity.class);
        System.out.println(JSON.toJSONString(metadata.toJson(), SerializerFeature.PrettyFormat));

        assertNotNull(metadata);
        assertNotNull(metadata.getInputs());

        assertEquals(1, metadata.getInputs().size());
    }

    @Getter
    @Setter
    public static class TestEntity {
        @Schema
        private String name;
    }

}