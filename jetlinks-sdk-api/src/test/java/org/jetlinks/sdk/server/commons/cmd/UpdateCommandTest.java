package org.jetlinks.sdk.server.commons.cmd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.annotation.command.CommandHandler;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.supports.command.JavaBeanCommandSupport;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class UpdateCommandTest {


    @Test
    void testMetadata() {

        JavaBeanCommandSupport support = new JavaBeanCommandSupport(new MyCommandService());

        FunctionMetadata metadata = support.getCommandMetadata("Update")
            .block();

        assertNotNull(metadata);

        assertNotNull(metadata.getInputs());
        System.out.println(JSON.toJSONString(metadata.toJson(), SerializerFeature.PrettyFormat));

    }


    public static class MyCommandService implements MyCommandServiceApi<TestEntity> {

        @Override
        public Mono<Void> update(UpdateCommand<TestEntity> command) {
            return Mono.empty();
        }
    }

    public interface MyCommandServiceApi<T> {

        @CommandHandler
        Mono<Void> update(UpdateCommand<T> command);
    }

    @Getter
    @Setter
    public static class TestEntity {
        @Schema(title = "ID")
        private String id;
    }
}