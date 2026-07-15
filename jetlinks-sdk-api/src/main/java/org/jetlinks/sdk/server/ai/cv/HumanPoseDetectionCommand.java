package org.jetlinks.sdk.server.ai.cv;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;

/**
 * 执行人体姿势识别命令,并获取实时输出. 当输出被dispose,则认为任务停止.
 *
 * @author liusq
 */
@Schema(title = "人体姿势识别")
public class HumanPoseDetectionCommand extends ComputerVisionCommand<ObjectDetectionResult, HumanPoseDetectionCommand> {

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(HumanPoseDetectionCommand.class);
    }
}
