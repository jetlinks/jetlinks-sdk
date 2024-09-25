package org.jetlinks.sdk.server.file;

import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.commons.cmd.OperationByIdCommand;
import reactor.core.publisher.Mono;

/**
 * 删除文件命令
 */
@Schema(title = "删除文件")
public class DeleteFileCommand extends OperationByIdCommand<Mono<Void>, DeleteFileCommand> {

    public static FunctionMetadata metadata() {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(DeleteFileCommand.class));
        metadata.setName("删除文件");
        metadata.setInputs(Lists.newArrayList(
            SimplePropertyMetadata.of("id", "Id", new ArrayType().elementType(StringType.GLOBAL))
        ));
        return metadata;
    }


}
