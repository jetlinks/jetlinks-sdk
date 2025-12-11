package org.jetlinks.sdk.server.ai;

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

    /**
     * 复判任务管理支持
     */
    String aiReviewManager = "aiReviewManager";

    /**
     * 复判数据管理支持
     */
    String aiReviewDataManager = "aiReviewDataManager";

    /**
     * 复判记录管理支持
     */
    String aiReviewRecordManager = "aiReviewRecordManager";

}
