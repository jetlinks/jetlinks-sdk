package org.jetlinks.sdk.server.commons.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.file.FileInfo;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 数据导出为文件命令
 */
public class ExportFileCommand extends AbstractExportCommand<Mono<FileInfo>, ExportFileCommand> {

    public static <T> CommandHandler<ExportFileCommand, Mono<FileInfo>> createHandler(
            Consumer<SimpleFunctionMetadata> custom,
            Function<ExportFileCommand, Mono<FileInfo>> handler) {

        return CommandHandler.of(
                () -> {
                    SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                    metadata.setId(CommandUtils.getCommandIdByType(ExportFileCommand.class));
                    metadata.setName("导出数据为文件");
                    metadata.setDescription("根据条件导出对应数据为文件");
                    metadata.setInputs(getParameterMetadata());
                    metadata.setOutput(StringType.GLOBAL);
                    custom.accept(metadata);
                    return metadata;
                },
                (cmd, ignore) -> handler.apply(cmd),
                ExportFileCommand::new
        );

    }

}
