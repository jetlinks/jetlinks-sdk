package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.sdk.server.commons.cmd.BatchDataCommand;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author wangsheng
 */
@Schema(description = "批量创建并启用设备")
public class SaveAndEnabledDeviceCommand<T> extends BatchDataCommand<T, SaveAndEnabledDeviceCommand<T>> {

    public static FunctionMetadata metadata(Consumer<SimpleFunctionMetadata> custom) {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(SaveAndEnabledDeviceCommand.class));
        metadata.setName("批量创建并启用设备");
        metadata.setInputs(CommandMetadataResolver.resolveInputs(ResolvableType.forType(SaveAndEnabledDeviceCommand.class)));
        custom.accept(metadata);
        return metadata;
    }


    public static <T> CommandHandler<SaveAndEnabledDeviceCommand<T>, Flux<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<SaveAndEnabledDeviceCommand<T>, Flux<T>> handler,
        ResolvableType elementType) {
        return createHandler(custom, handler, CommandUtils.createConverter(elementType));
    }


    public static <T> CommandHandler<SaveAndEnabledDeviceCommand<T>, Flux<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<SaveAndEnabledDeviceCommand<T>, Flux<T>> handler,
        Function<Object, T> resultConverter) {
        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            () -> new SaveAndEnabledDeviceCommand<T>().withConverter(resultConverter)
        );
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(SaveAndEnabledDeviceCommand.class);
    }
}
