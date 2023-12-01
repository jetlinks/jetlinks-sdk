package org.jetlinks.sdk.server.handler;

import lombok.NoArgsConstructor;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.metadata.DataType;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.sdk.server.commons.cmd.SubscribeCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author gyl
 * @since 1.0
 */
@NoArgsConstructor
public class EntitySubscribeCommandRegisterHandler extends SubscribeCommandHandler {

    public void register(BiConsumer<String, CommandHandler<EntityEventSubscribeCommand, Flux<Object>>> support,
                         DataType entityDataType,
                         Function<Function<Object, Mono<Object>>, Function<Object, Mono<Void>>> handler) {
        for (EntitySubscribeType type : EntitySubscribeType.values()) {
            support
                .accept(type.getId(),
                        EntityEventSubscribeCommand.createHandler(
                            () -> {
                                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                                metadata.setId(type.getId());
                                metadata.setName(type.getName());
                                metadata.setDescription(type.getDescription());
                                metadata.setOutput(type.getOutput().apply(entityDataType));
                                return metadata;
                            },
                            (cmd, callback) -> this
                                .addCallback0(type.getEventClass(),
                                              obj -> handler.apply(callback).apply(obj)),
                            () -> new EntityEventSubscribeCommand().withCommandId(type.getId()))
                );
        }
    }

    public static class EntityEventSubscribeCommand extends SubscribeCommand<Object, EntityEventSubscribeCommand> {

        @Override
        public String getCommandId() {
            return (String) Optional
                .ofNullable(readable().get("commandId"))
                .orElse(super.getCommandId());
        }

        public EntityEventSubscribeCommand withCommandId(String commandId) {
            writable().put("commandId", commandId);
            return this;
        }

    }


}
