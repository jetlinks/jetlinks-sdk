package org.jetlinks.sdk.server.ai.cv;

import io.swagger.v3.oas.annotations.media.Schema;
import reactor.core.publisher.Flux;

/**
 * 图像比对
 *
 */
@Schema(title = "图像比对")
public class ImageComparisonCommand
    extends ComputerVisionCommand<Flux<ImageComparisonResult>, ImageComparisonCommand> {


}
