package org.jetlinks.sdk.server.ai.cv;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.ai.AiCommand;
import org.jetlinks.sdk.server.ai.AiOutput;
import org.jetlinks.sdk.server.ai.InternalCVTaskTarget;
import org.jetlinks.sdk.server.ai.model.AiModelInfo;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 机器视觉命令抽象类,实现此类来定义AI机器视觉相关命令
 *
 * @param <R>    命令返回类型
 * @param <Self> 自身类型
 * @author zhouhao
 * @see InternalCVTaskTarget#ObjectDetection
 * @see InternalCVTaskTarget#ImageRecognition
 * @since 1.0.1
 */
public abstract class ComputerVisionCommand<R extends AiOutput<R>, Self extends ComputerVisionCommand<R, Self>>
    extends AbstractCommand<Flux<R>, Self> implements AiCommand<R> {

    protected ComputerVisionCommand() {
    }

    @Schema(title = "任务ID")
    public String getTaskId() {
        return getOrNull("taskId", String.class);
    }

    public Self setTaskId(String taskId) {
        return with("taskId", taskId);
    }


    @Schema(title = "视频源")
    public List<MediaSource> getSources() {
        return ConverterUtils.convertToList(
            readable().get("sources"),
            val -> val instanceof MediaSource ? ((MediaSource) val) : FastBeanCopier.copy(val, new MediaSource()));
    }

    public Self setSources(List<MediaSource> sources) {
        return with("sources", sources);
    }

    /**
     * @return 算法模型ID
     * @see AiModelInfo#getId()
     * @see org.jetlinks.sdk.server.ai.AiCommandSupports#modelManager
     * @see org.jetlinks.sdk.server.SdkServices#aiService
     */
    @Schema(title = "算法模型ID")
    public String getModelId() {
        return getOrNull("modelId", String.class);
    }

    public Self setModelId(String modelId) {
        return with("modelId", modelId);
    }

}
