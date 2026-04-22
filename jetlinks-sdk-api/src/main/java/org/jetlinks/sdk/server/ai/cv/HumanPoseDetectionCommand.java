package org.jetlinks.sdk.server.ai.cv;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.ai.AiOutputMetadata;
import org.springframework.core.ResolvableType;

/**
 * 执行人体姿势识别命令,并获取实时输出. 当输出被dispose,则认为任务停止.
 *
 * @author liusq
 */
@Schema(title = "人体姿势识别")
public class HumanPoseDetectionCommand extends ComputerVisionCommand<ObjectDetectionResult, HumanPoseDetectionCommand> {

    public static final AiOutputMetadata aiOutputMetadata = AiOutputMetadata.of(
        ResolvableType.forClass(ObjectDetectionResult.class),
        ResolvableType.forClass(ObjectDetectionResult.FlatData.class)
    );

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(HumanPoseDetectionCommand.class);
    }
}
