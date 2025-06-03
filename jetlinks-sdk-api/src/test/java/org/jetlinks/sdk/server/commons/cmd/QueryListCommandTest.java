package org.jetlinks.sdk.server.commons.cmd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryListCommandTest {


    @Test
    void test() {
        System.out.println(JSON.toJSONString(
            QueryListCommand.getQueryParamMetadata(),
            SerializerFeature.PrettyFormat));

    }

    @Test
    void testMetadata() {
        System.out.println(JSON.toJSONString(
            QueryListCommand.metadata(String.class),
            SerializerFeature.PrettyFormat));

    }
}