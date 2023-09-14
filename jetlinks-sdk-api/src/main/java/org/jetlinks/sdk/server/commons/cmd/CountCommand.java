package org.jetlinks.sdk.server.commons.cmd;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.types.IntType;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 分页查询数据指令
 *
 * @param <T> 数据类型
 * @author zhouhao
 * @see QueryCommand
 * @since 2.1
 */
@Getter
@Setter
public class CountCommand extends QueryCommand<Mono<Integer>, CountCommand> {


    public static  CommandHandler<CountCommand, Mono<Integer>> createHandler(Consumer<SimpleFunctionMetadata> custom,
                                                                                   Function<CountCommand, Mono<Integer>> handler) {


        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                //Count
                metadata.setId(CommandUtils.getCommandIdByType(CountCommand.class));
                metadata.setName("查询数量");
                metadata.setDescription("查询符合条件的数据数量");
                metadata.setInputs(QueryListCommand.getQueryParamMetadata());
                metadata.setOutput(IntType.GLOBAL);
                custom.accept(metadata);
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            CountCommand::new
        );

    }

}
