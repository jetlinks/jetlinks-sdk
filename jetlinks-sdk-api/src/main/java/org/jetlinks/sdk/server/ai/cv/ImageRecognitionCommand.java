package org.jetlinks.sdk.server.ai.cv;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.ai.AICommandHandler;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;

import java.util.function.Function;

/**
 * 图像识别
 *
 * @since 1.0.1
 */
@Schema(title = "图像识别")
public class ImageRecognitionCommand
    extends ComputerVisionCommand<ObjectDetectionResult, ImageRecognitionCommand> {

    public static AICommandHandler<ImageRecognitionCommand, Flux<ObjectDetectionResult>> createHandler
        (Function<ImageRecognitionCommand, Flux<ObjectDetectionResult>> executor) {
        return AICommandHandler.of(ImageRecognitionCommand::metadata,
                                   (cmd, s) -> executor.apply(cmd),
                                   ImageRecognitionCommand::new,
                                   ResolvableType.forClass(ObjectDetectionResult.class),
                                   ResolvableType.forClass(ObjectDetectionResult.FlatData.class));
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(ImageRecognitionCommand.class);
    }

}
