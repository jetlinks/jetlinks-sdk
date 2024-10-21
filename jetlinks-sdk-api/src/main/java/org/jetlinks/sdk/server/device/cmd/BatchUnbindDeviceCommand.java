package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

public class BatchUnbindDeviceCommand extends AbstractCommand<Mono<Void>, BatchUnbindDeviceCommand> {

    public static final String TYPE = "type";

    public static final String DEVICE_ID = "deviceId";

    public String getType() {
        return getOrNull(TYPE, String.class);
    }

    public BatchUnbindDeviceCommand setType(String type) {
        return with(TYPE, type);
    }

    public List<String> getDeviceId() {
        return ConverterUtils
            .convertToList(readable().get(DEVICE_ID), String::valueOf);
    }

    public BatchUnbindDeviceCommand setDeviceId(List<String> deviceId) {
        return with(DEVICE_ID, deviceId);
    }

    public static CommandHandler<BatchUnbindDeviceCommand, Mono<Void>> createHandler(Function<BatchUnbindDeviceCommand, Mono<Void>> handler) {
        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(BatchUnbindDeviceCommand.class));
                metadata.setName("批量解除设备绑定映射");
                metadata.setInputs(CommandMetadataResolver.resolveInputs(ResolvableType.forType(BatchUnbindDeviceCommand.class)));
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            BatchUnbindDeviceCommand::new
        );
    }
}
