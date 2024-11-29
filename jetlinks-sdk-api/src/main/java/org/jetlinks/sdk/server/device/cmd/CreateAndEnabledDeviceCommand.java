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
public class CreateAndEnabledDeviceCommand<T> extends BatchDataCommand<T, CreateAndEnabledDeviceCommand<T>> {

    public static FunctionMetadata metadata(Consumer<SimpleFunctionMetadata> custom) {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(CreateAndEnabledDeviceCommand.class));
        metadata.setName("批量创建并启用设备");
        metadata.setInputs(CommandMetadataResolver.resolveInputs(ResolvableType.forType(CreateAndEnabledDeviceCommand.class)));
        custom.accept(metadata);
        return metadata;
    }


    public static <T> CommandHandler<CreateAndEnabledDeviceCommand<T>, Flux<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<CreateAndEnabledDeviceCommand<T>, Flux<T>> handler,
        ResolvableType elementType) {
        return createHandler(custom, handler, CommandUtils.createConverter(elementType));
    }


    public static <T> CommandHandler<CreateAndEnabledDeviceCommand<T>, Flux<T>> createHandler(
        Consumer<SimpleFunctionMetadata> custom,
        Function<CreateAndEnabledDeviceCommand<T>, Flux<T>> handler,
        Function<Object, T> resultConverter) {
        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            () -> new CreateAndEnabledDeviceCommand<T>().withConverter(resultConverter)
        );
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(CreateAndEnabledDeviceCommand.class);
    }
}
