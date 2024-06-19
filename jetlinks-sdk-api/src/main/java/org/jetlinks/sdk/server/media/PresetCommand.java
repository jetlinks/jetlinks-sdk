package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * 云台控制命令
 *
 * @author zhouhao
 * @since 1.0
 */
public class PresetCommand extends AbstractCommand<Mono<Void>, PresetCommand> {

    @Schema(description = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public PresetCommand setDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    @Schema(description = "通道ID")
    public String getChannelId() {
        return getOrNull("channelId", String.class);
    }

    public PresetCommand setChannelId(String channelId) {
        return with("channelId", channelId);
    }

    @Schema(defaultValue = "预置位ID")
    public String getId() {
        return getOrNull("id", String.class);
    }

    public PresetCommand setId(String id) {
        return with("id", id);
    }

    @Schema(defaultValue = "预置位操作")
    public Operation getOperation() {
        return getOrNull("operation", Operation.class);
    }

    public PresetCommand setOperation(Operation operation) {
        return with("operation", operation);
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(ResolvableType.forType(PresetCommand.class));
    }

    @AllArgsConstructor
    @Getter
    public enum Operation {
        //设置预置位
        SET(0x81),
        //调用预置位
        CALL(0x82),
        //删除预置位
        DEL(0x83);

        private final int code;

        public static Operation of(Object operation) {
            for (Operation value : values()) {
                if (value.name().equalsIgnoreCase(String.valueOf(operation)) ||
                    operation.equals(value.code)
                ) {
                    return value;
                }
            }
            throw new UnsupportedOperationException("不支持的预置位操作:" + operation);
        }

        public static Optional<Operation> of(int code) {
            for (Operation value : values()) {
                if (value.code == code) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }
}
