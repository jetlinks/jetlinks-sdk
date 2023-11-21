package org.jetlinks.sdk.server.commons.cmd;

import org.hswebframework.ezorm.core.dsl.Delete;
import org.hswebframework.ezorm.core.param.Param;
import org.hswebframework.ezorm.core.param.Term;
import org.hswebframework.ezorm.rdb.mapping.DSLDelete;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 删除命令
 * <pre>{@code
 *
 * {   //等同sql：  delete [table] where id = 'data-1'
 *     "terms":[   //指定删除的条件
 *      {
 *          "column":"id",
 *          "value":"data-1"
 *      }
 *    ]
 * }
 * }</pre>
 */
public class DeleteCommand extends AbstractCommand<Mono<Integer>, DeleteCommand> {

    public static final String PARAMETER_TERMS = "terms";


    public DeleteCommand dsl(Consumer<Delete<Param>> consumer) {
        Delete<Param> update = Delete.of();

        consumer.accept(update);

        with(PARAMETER_TERMS, update.getParam().getTerms());

        return castSelf();

    }

    public <U extends DSLDelete<?>> U applyDelete(U delete) {

        Param param = toParameter();

        for (Term term : param.getTerms()) {
            delete.accept(term);
        }

        return delete;

    }

    public Param toParameter() {

        Param param = new Param();
        Object terms = readable().get(PARAMETER_TERMS);
        Assert.notNull(terms, "'terms' can not be null");
        param.setTerms(ConverterUtils.convertTerms(terms));

        return param;
    }

    public static CommandHandler<DeleteCommand, Mono<Integer>> createHandler(Consumer<SimpleFunctionMetadata> custom,
                                                                             Function<DeleteCommand, Mono<Integer>> handler) {


        return CommandHandler.of(
                () -> {
                    SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                    //Delete
                    metadata.setId(CommandUtils.getCommandIdByType(DeleteCommand.class));
                    metadata.setName("删除数据");
                    metadata.setDescription("根据条件删除对应数据");
                    custom.accept(metadata);
                    return metadata;
                },
                (cmd, ignore) -> handler.apply(cmd),
                DeleteCommand::new
        );

    }


}
