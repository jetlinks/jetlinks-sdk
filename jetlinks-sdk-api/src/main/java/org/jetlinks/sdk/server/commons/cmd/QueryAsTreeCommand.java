package org.jetlinks.sdk.server.commons.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.PropertyMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 查询树结构数据列表命令
 *
 * @param <T> 数据类型
 * @author zhouhao
 * @see QueryCommand
 * @since 2.1
 */
public class QueryAsTreeCommand<T> extends QueryCommand<Flux<T>, QueryAsTreeCommand<T>> {


    public static <T> CommandHandler<QueryAsTreeCommand<T>, Flux<T>> createHandler(Consumer<SimpleFunctionMetadata> custom,
                                                                                   Function<QueryAsTreeCommand<T>, Flux<T>> handler) {


        return CommandHandler.of(
                () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                //QueryAsTree
                metadata.setId(CommandUtils.getCommandIdByType(QueryAsTreeCommand.class));
                metadata.setName(metadata.getId());
                metadata.setName("查询列表返回树结构");
                metadata.setDescription("条件查询列表,并将返回的数据组装为树结构");
                metadata.setInputs(getQueryParamMetadata());
                custom.accept(metadata);
                return metadata;
            },
                (cmd, ignore) -> handler.apply(cmd),
                QueryAsTreeCommand::new
        );

    }

    public static List<PropertyMetadata> getQueryParamMetadata() {
        return Arrays.asList(
            getTermsMetadata(),
            SimplePropertyMetadata.of("sorts", "排序", new ArrayType().elementType(
                new ObjectType()
                    .addProperty("name", "列名(属性名)", StringType.GLOBAL)
                    .addProperty("order", "排序方式,如:asc,desc", StringType.GLOBAL)
            ))
        );
    }

}
