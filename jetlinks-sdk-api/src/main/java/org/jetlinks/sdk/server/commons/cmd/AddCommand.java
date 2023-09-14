package org.jetlinks.sdk.server.commons.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Function;

public class AddCommand<T> extends BatchDataCommand<T, AddCommand<T>> {

    public static <T> CommandHandler<AddCommand<T>, Flux<T>> createHandler(Consumer<SimpleFunctionMetadata> custom,
                                                                           Function<AddCommand<T>, Flux<T>> handler) {


        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                //Add
                metadata.setId(CommandUtils.getCommandIdByType(AddCommand.class));
                metadata.setName("新增数据");
                metadata.setDescription("批量新增数据");
                custom.accept(metadata);
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            AddCommand::new
        );

    }


}
