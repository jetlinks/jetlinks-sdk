package org.jetlinks.sdk.server.ai.cv;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.ai.AICommandHandler;
import org.jetlinks.sdk.server.ai.AiOutputMetadata;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;

import java.util.function.Function;

/**
 * 执行目标检测命令,并获取实时输出. 当输出被dispose,则认为任务停止.
 *
 * @author zhouhao
 * @since 2.2
 */
@Schema(title = "目标检测")
public class ObjectDetectionCommand extends ComputerVisionCommand<ObjectDetectionResult, ObjectDetectionCommand> {

    public static final AiOutputMetadata aiOutputMetadata = AiOutputMetadata.of(
        ResolvableType.forClass(ObjectDetectionResult.class),
        ResolvableType.forClass(ObjectDetectionResult.FlatData.class)
    );

    @Deprecated
    public static AICommandHandler<ObjectDetectionCommand, Flux<ObjectDetectionResult>> createHandler
        (Function<ObjectDetectionCommand, Flux<ObjectDetectionResult>> executor) {
        return AICommandHandler.of(ObjectDetectionCommand::metadata,
                                   (cmd, s) -> executor.apply(cmd),
                                   ObjectDetectionCommand::new,
                                   ResolvableType.forClass(ObjectDetectionResult.class),
                                   ResolvableType.forClass(ObjectDetectionResult.FlatData.class));
    }


    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(ObjectDetectionCommand.class);
    }
}
