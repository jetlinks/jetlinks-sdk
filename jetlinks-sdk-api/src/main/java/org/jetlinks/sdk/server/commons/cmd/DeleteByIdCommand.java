package org.jetlinks.sdk.server.commons.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.StringType;

import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author zhouhao
 * @since 2.1
 */
public class DeleteByIdCommand<T> extends OperationByIdCommand<T, DeleteByIdCommand<T>> {

    public static <T> CommandHandler<DeleteByIdCommand<T>, T> createHandler(Consumer<SimpleFunctionMetadata> custom,
                                                                            Function<DeleteByIdCommand<T>, T> handler) {


        return CommandHandler.of(
                () -> {
                    SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                    //DeleteById
                    metadata.setId(CommandUtils.getCommandIdByType(DeleteByIdCommand.class));
                    metadata.setName("根据id删除");
                    metadata.setInputs(Collections.singletonList(SimplePropertyMetadata.of("id", "Id", StringType.GLOBAL)));
                    custom.accept(metadata);
                    return metadata;
                },
                (cmd, ignore) -> handler.apply(cmd),
                DeleteByIdCommand::new
        );

    }


}
