package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 云台控制命令
 *
 * @author zhouhao
 * @since 1.0
 */
public class PTZCommand extends AbstractCommand<Mono<Void>, PTZCommand> {

    @Schema(description = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public PTZCommand setDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    @Schema(description = "通道ID")
    public String getChannelId() {
        return getOrNull("channelId", String.class);
    }

    public PTZCommand setChannelId(String channelId) {
        return with("channelId", channelId);
    }

    @Schema(description = "操作,key为操作类型,value为操作速度.")
    public Map<Direct, Integer> getOperations() {
        Map<Direct, Integer> opt = getOrNull("operations", ResolvableType
            .forClassWithGenerics(HashMap.class, Direct.class, Integer.class)
            .getType());
        return opt == null ? Collections.emptyMap() : opt;
    }

    public PTZCommand setOperations(Map<Direct, Integer> operations) {
        return with("operations", operations);
    }

    public PTZCommand setOperations(Direct direct, int speed) {
        return with("operations", Collections.singletonMap(direct, speed));
    }

    @Schema(description = "延迟自动停止")
    public Integer getStopDelaySeconds() {
        return getOrNull("stopDelaySeconds", Integer.class);
    }

    public PTZCommand setStopDelaySeconds(Integer stopDelaySeconds) {
        return with("stopDelaySeconds", stopDelaySeconds);
    }


    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(ResolvableType.forType(PTZCommand.class));
    }

    public enum Direct {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        ZOOM_IN,
        ZOOM_OUT,
        STOP;
        private static final Direct[] VALUES = Direct.values();

        public static Direct of(String name) {
            for (Direct value : VALUES) {
                if (value.name().equalsIgnoreCase(name)) {
                    return value;
                }
            }
            return null;
        }
    }
}
