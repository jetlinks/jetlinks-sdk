package org.jetlinks.sdk.server.ui;

import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.message.MessageType;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.ui.field.annotation.field.ui.DeviceDownStreamComponent;
import org.jetlinks.sdk.server.ui.field.annotation.field.ui.UiComponent;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UiComponentTest {


    @Test
    public void testUiComponent() {

        FunctionMetadata resolve = CommandMetadataResolver.resolve(Test1Command.class);
        assertFalse(resolve.getExpands().isEmpty());
        assertTrue(resolve.getExpands().containsKey(UiComponent.KEY));
        Map<String, Object> map = FastBeanCopier.copy(resolve.getExpands().get(UiComponent.KEY), new HashMap<>());
        assertTrue(Objects.nonNull(map.get("component")) && map.get("component").equals("deviceDownStream"));
        assertTrue(Objects.nonNull(map.get("type")) && MessageType.INVOKE_FUNCTION.equals(map.get("type")));
    }

    @DeviceDownStreamComponent(type = MessageType.INVOKE_FUNCTION)
    public static class Test1Command extends AbstractCommand<Mono<String>, Test1Command> {

    }


}
