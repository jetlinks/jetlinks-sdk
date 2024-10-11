package org.jetlinks.sdk.server.template;

import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.types.BooleanType;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Function;

/**
 * 根据模板保存数据
 *
 * @author gyl
 * @since 1.0.1
 */
public class SaveByTemplateCommand extends AbstractConvertCommand<Flux<SaveByTemplateData>, SaveByTemplateCommand> {


    public static final String PARAMETER_KEY = "data";

    public final SaveByTemplateCommand withTemplate(List<EntityTemplateInfo> entityInstances) {

        writable().put(PARAMETER_KEY, entityInstances);

        return castSelf();
    }

    public final List<EntityTemplateInfo> templateList() {
        return ConverterUtils.convertToList(readable().get(PARAMETER_KEY), EntityTemplateInfo::of);
    }

    public final <E> List<E> templateList(Function<EntityTemplateInfo, E> converter) {
        return ConverterUtils.convertToList(readable().get(PARAMETER_KEY), f -> converter.apply(EntityTemplateInfo.of(f)));
    }

    public static CommandHandler<SaveByTemplateCommand, Flux<SaveByTemplateData>> createHandler(
        Function<SaveByTemplateCommand, Flux<SaveByTemplateData>> handler) {
        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(SaveByTemplateCommand.class));
                metadata.setName("根据模板保存数据");
                metadata.setInputs(EntityTemplateInfo.parseMetadata());
                metadata.setOutput(new ObjectType()
                                       .addProperty("id", "id", StringType.GLOBAL)
                                       .addProperty("success", "是否成功", BooleanType.GLOBAL)
                                       .addProperty("errorMessage", "错误信息", StringType.GLOBAL)
                                       .addProperty("data", "data", new ObjectType()));
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            SaveByTemplateCommand::new
        );
    }

}
