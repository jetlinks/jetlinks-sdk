package org.jetlinks.sdk.server.commons.cmd;

import org.hswebframework.ezorm.core.dsl.Update;
import org.hswebframework.ezorm.core.param.Term;
import org.hswebframework.ezorm.core.param.UpdateParam;
import org.hswebframework.ezorm.rdb.mapping.DSLUpdate;
import org.hswebframework.web.exception.BusinessException;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.types.IntType;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 修改数据指令
 *
 * <pre>{@code
 *
 * {   //等同sql：  update [table] set name  = 'newName' where id = 'data-1'
 *     "data":{    //要更新的结果
 *         "name":"newName"
 *     },
 *     "terms":[   //指定更新的条件
 *      {
 *          "column":"id",
 *          "value":"data-1"
 *      }
 *     ]
 * }
 * }</pre>
 *
 * @author zhouhao
 * @since 2.1
 */
public class UpdateCommand<T> extends AbstractCommand<Mono<Integer>, UpdateCommand<T>> {

    public static final String PARAMETER_DATA = "data";

    public static final String PARAMETER_TERMS = "terms";

    public UpdateCommand<T> dsl(T data, Consumer<Update<T, UpdateParam<T>>> consumer) {
        Update<T, UpdateParam<T>> update = Update.of(data);

        consumer.accept(update);

        with(PARAMETER_DATA, update.getParam().getData());
        with(PARAMETER_TERMS, update.getParam().getTerms());

        return castSelf();

    }

    public <E extends T, U extends DSLUpdate<E, ?>> U applyUpdate(U update, Function<Object, E> mapper) {

        UpdateParam<E> param = toParameter(mapper);
        E data = param.getData();
        update.set(data);
        if (data instanceof Map) {
            ((Map<?, ?>) data)
                .forEach((key, value) -> {
                    if (value == null) {
                        if (key.equals("id")){
                            throw new BusinessException("error.update_id_can_not_be_null");
                        }
                        //更新null值
                        update.setNull(String.valueOf(key));
                    }
                });
        }

        for (Term term : param.getTerms()) {
            update.accept(term);
        }
        return update;

    }

    public <E> UpdateParam<E> toParameter(Function<Object, E> mapper) {

        Object data = readable().get(PARAMETER_DATA);
        Object terms = readable().get(PARAMETER_TERMS);

        Assert.isInstanceOf(Map.class, data, "illegal 'data' format");
        Assert.notNull(terms, "'terms' can not be null");

        UpdateParam<E> param = new UpdateParam<>(mapper.apply(data));

        param.setTerms(ConverterUtils.convertTerms(terms));

        return param;
    }

    public static <T> CommandHandler<UpdateCommand<T>, Mono<Integer>> createHandler(Consumer<SimpleFunctionMetadata> custom,
                                                                                    Function<UpdateCommand<T>, Mono<Integer>> handler) {


        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                //Update
                metadata.setId(CommandUtils.getCommandIdByType(UpdateCommand.class));
                metadata.setName("更新数据");
                metadata.setDescription("更新条件不得为空");
                metadata.setOutput(IntType.GLOBAL);
                custom.accept(metadata);
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            UpdateCommand::new
        );

    }


}
