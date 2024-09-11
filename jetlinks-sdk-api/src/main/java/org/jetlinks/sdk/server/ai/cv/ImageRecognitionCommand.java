package org.jetlinks.sdk.server.ai.cv;

import io.swagger.v3.oas.annotations.media.Schema;
import reactor.core.publisher.Flux;

/**
 * 图像识别
 *
 * @since 1.0.1
 */
@Schema(title = "图像识别")
public class ImageRecognitionCommand
    extends ComputerVisionCommand<Flux<ObjectDetectionResult>, ImageRecognitionCommand> {


}
