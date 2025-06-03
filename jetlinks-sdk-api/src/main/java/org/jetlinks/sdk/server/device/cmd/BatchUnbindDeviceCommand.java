package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Function;

public class BatchUnbindDeviceCommand<T> extends AbstractConvertCommand<Flux<T>, BatchUnbindDeviceCommand<T>> {

    public static final String TYPE = "type";

    public static final String KEY = "key";

    public static final String DEVICE_ID = "deviceId";

    public String getType() {
        return getOrNull(TYPE, String.class);
    }

    public BatchUnbindDeviceCommand<T> setType(String type) {
        return with(TYPE, type);
    }

    public List<String> getDeviceId() {
        return ConverterUtils
            .convertToList(readable().get(DEVICE_ID), String::valueOf);
    }

    public BatchUnbindDeviceCommand<T> setDeviceId(List<String> deviceId) {
        return with(DEVICE_ID, deviceId);
    }

    public List<String> getKey() {
        return ConverterUtils
            .convertToList(readable().get(KEY), String::valueOf);
    }

    public BatchUnbindDeviceCommand<T> setKey(List<String> key) {
        return with(KEY, key);
    }

    public static <T> CommandHandler<BatchUnbindDeviceCommand<T>, Flux<T>> createHandler(Function<BatchUnbindDeviceCommand<T>, Flux<T>> handler,
                                                                                         Function<Object, T> resultConverter) {
        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(BatchUnbindDeviceCommand.class));
                metadata.setName("批量解除设备绑定映射");
                metadata.setInputs(CommandMetadataResolver.resolveInputs(ResolvableType.forType(BatchUnbindDeviceCommand.class)));
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            () -> new BatchUnbindDeviceCommand<T>().withConverter(resultConverter)
        );
    }
}
