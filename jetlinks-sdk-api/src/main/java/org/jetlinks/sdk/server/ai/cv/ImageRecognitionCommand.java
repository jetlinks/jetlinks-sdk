package org.jetlinks.sdk.server.ai.cv;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.PropertyMetadata;
import org.springframework.core.ResolvableType;

import java.util.List;

/**
 * 图像识别
 *
 * @since 1.0.1
 */
@Schema(title = "图像识别")
public class ImageRecognitionCommand
    extends ComputerVisionCommand<ObjectDetectionResult, ImageRecognitionCommand> {

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(ImageRecognitionCommand.class);
    }


    @Override
    public List<PropertyMetadata> getFlatMapMetadata() {
        return getClassMetadata(ResolvableType.forClass(ObjectDetectionResult.RuleData.class));
    }
}
