package org.jetlinks.sdk.server.commons.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.collections4.CollectionUtils;
import org.hswebframework.ezorm.core.CastUtil;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author liusq
 */
@Schema(title = "启用", description = "启用或激活")
public class EnabledCommand extends OperationByIdCommand<Mono<Void>, EnabledCommand> {
    public static final String PARAMETER_KEY = "idList";

    public List<String> getIds() {
        List<String> compatible = ConverterUtils.convertToList(readable().get(PARAMETER_KEY), String::valueOf);
        return CollectionUtils.isEmpty(compatible) ? getIdList(String::valueOf) : compatible;
    }

    public static CommandHandler<EnabledCommand, Mono<Void>> createHandler(Function<EnabledCommand, Mono<Void>> handler) {
        return CommandHandler.of(
            () -> metadata(functionMetadata -> {
            }),
            (cmd, ignore) -> handler.apply(cmd),
            EnabledCommand::new
        );

    }

    public static CommandHandler<EnabledCommand, Mono<Void>> createHandler(Function<EnabledCommand, Mono<Void>> handler,
                                                                           Consumer<SimpleFunctionMetadata> custom) {
        return CommandHandler.of(
            () -> metadata(custom),
            (cmd, ignore) -> handler.apply(cmd),
            EnabledCommand::new
        );

    }

    public static FunctionMetadata metadata(Consumer<SimpleFunctionMetadata> custom) {
        FunctionMetadata metadata = CommandMetadataResolver.resolve(EnabledCommand.class);
        custom.accept(CastUtil.cast(metadata));
        return metadata;
    }

}
