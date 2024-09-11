package org.jetlinks.sdk.server.ai.cv;

import io.swagger.v3.oas.annotations.media.Schema;
import reactor.core.publisher.Flux;

/**
 * 执行目标检测命令,并获取实时输出. 当输出被dispose,则认为任务停止.
 *
 * @author zhouhao
 * @since 2.2
 */
@Schema(title = "目标检测")
public class ObjectDetectionCommand extends ComputerVisionCommand<Flux<ObjectDetectionResult>, ObjectDetectionCommand> {


}
