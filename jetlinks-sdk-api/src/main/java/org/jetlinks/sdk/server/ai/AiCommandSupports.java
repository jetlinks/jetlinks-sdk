package org.jetlinks.sdk.server.ai;

/**
 * @deprecated {@link org.jetlinks.pro.ai.cmd.AiServices}
 */
@Deprecated
public interface AiCommandSupports {

    /**
     * 模型管理支持
     * <p>
     * aiService:modelManager
     *
     * @see org.jetlinks.sdk.server.ai.model.AiModelInfo
     * @see org.jetlinks.sdk.server.SdkServices#aiService
     */
    String modelManager = "modelManager";

    /**
     * 任务管理支持
     * <p>
     *
     * @see org.jetlinks.sdk.server.ai.model.AiModelInfo
     * @see org.jetlinks.sdk.server.SdkServices#aiService
     */
    String taskManager = "taskManager";

}
