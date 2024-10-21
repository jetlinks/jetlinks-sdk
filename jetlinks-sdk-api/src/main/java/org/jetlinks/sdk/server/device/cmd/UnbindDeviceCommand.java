package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class UnbindDeviceCommand extends AbstractCommand<Mono<Void>, UnbindDeviceCommand> {

    public static final String TYPE = "type";

    public static final String KEY = "key";

    public String getType() {
        return getOrNull(TYPE, String.class);
    }

    public UnbindDeviceCommand setType(String type) {
        return with(TYPE,type);
    }

    public String getKey() {
        return getOrNull(KEY, String.class);
    }

    public UnbindDeviceCommand setKey(String key) {
        return with(KEY, key);
    }

    public static CommandHandler<UnbindDeviceCommand, Mono<Void>> createHandler(Function<UnbindDeviceCommand, Mono<Void>> handler) {
        return CommandHandler.of(
                () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(UnbindDeviceCommand.class));
                metadata.setName("解除设备绑定映射");
                metadata.setInputs(CommandMetadataResolver.resolveInputs(ResolvableType.forType(UnbindDeviceCommand.class)));
                return metadata;
            },
                (cmd, ignore) -> handler.apply(cmd),
                UnbindDeviceCommand::new
        );
    }
}
