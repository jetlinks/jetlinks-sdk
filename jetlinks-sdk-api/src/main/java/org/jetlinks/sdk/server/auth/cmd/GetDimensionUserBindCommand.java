package org.jetlinks.sdk.server.auth.cmd;

import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.auth.DimensionUserBindInfo;
import org.jetlinks.sdk.server.auth.DimensionUserBindRequset;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

/**
 * 获取维度的用户绑定信息,即获取哪些用户绑定了对应维度
 *
 * @author zhouhao
 * @see org.hswebframework.web.authorization.Dimension
 * @see org.hswebframework.web.authorization.Authentication
 * @since 1.0
 */
public class GetDimensionUserBindCommand extends AbstractConvertCommand<Flux<DimensionUserBindInfo>, GetDimensionUserBindCommand> {
    private static final long serialVersionUID = 1L;


    public List<DimensionUserBindRequset> getDimensions() {
        return ConverterUtils.convertToList(readable().get("dimensions"), DimensionUserBindRequset::of);
    }

    public GetDimensionUserBindCommand withDimensions(Collection<DimensionUserBindRequset> dimensions) {
        writable().put("dimensions", dimensions);
        return this;
    }

    @SuppressWarnings("all")
    public GetDimensionUserBindCommand withDimension(String type, String id) {
        Collection<DimensionUserBindRequset> dimensions = ((Collection) readable().getOrDefault("dimensions", new CopyOnWriteArrayList<>()));
        dimensions.add(new DimensionUserBindRequset(type, id));
        return this;
    }


    public static CommandHandler<GetDimensionUserBindCommand, Flux<DimensionUserBindInfo>> createHandler(
            Function<GetDimensionUserBindCommand, Flux<DimensionUserBindInfo>> handler
    ) {
        return CommandHandler.of(
                () -> {
                    SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                    metadata.setId(CommandUtils.getCommandIdByType(GetDimensionUserBindCommand.class));
                    metadata.setName("根据维度查询对应用户id");
                    metadata.setDescription("根据维度类型id查询对应所属的用户id");
                    metadata.setInputs(
                            Collections.singletonList(SimplePropertyMetadata.of("dimensions", "维度信息", new ObjectType()
                                    .addProperty("type", "维度类型", StringType.GLOBAL)
                                    .addProperty("id", "维度id", StringType.GLOBAL)
                            ))
                    );
                    return metadata;
                },
                (cmd, ignore) -> handler.apply(cmd),
                () -> new GetDimensionUserBindCommand()
                        .withConverter(CommandUtils.createConverter(ResolvableType.forType(DimensionUserBindInfo.class)))
        );
    }
}
