package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.ui.field.annotation.field.select.DeviceSelector;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;

@Schema(title = "获取配置信息")
public class GetConfigCommand extends AbstractCommand<Mono<Map<String, Object>>, GetConfigCommand> {

    public GetConfigCommand setId(String id) {
        return with("id", id);
    }

    public GetConfigCommand setConfigKeys(Collection<String> key) {
        return with("key", key);
    }

    @Schema(title = "ID")
    @DeviceSelector
    public String getId() {
        return getOrNull("id", String.class);
    }

    @Schema(title = "配置Key集合")
    public Collection<String> getConfigKey() {
        return getOrNull("key", Collection.class);
    }

}
