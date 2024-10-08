package org.jetlinks.sdk.server.template;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.sdk.server.commons.cmd.SaveCommand;
import reactor.core.publisher.Flux;

import java.util.function.Function;

/**
 * @author gyl
 * @since 1.0.1
 */
public class SaveEntityTemplateCommand extends SaveCommand<EntityTemplateInfo> {

    public static CommandHandler<SaveEntityTemplateCommand, Flux<EntityTemplateInfo>> createHandler(
            Function<SaveEntityTemplateCommand, Flux<EntityTemplateInfo>> handler) {
        return CommandHandler.of(
                () -> {
                    SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                    metadata.setId(CommandUtils.getCommandIdByType(SaveEntityTemplateCommand.class));
                    metadata.setName("保存资源模板数据");
                    return metadata;
                },
                (cmd, ignore) -> handler.apply(cmd),
                SaveEntityTemplateCommand::new
        );
    }

}
