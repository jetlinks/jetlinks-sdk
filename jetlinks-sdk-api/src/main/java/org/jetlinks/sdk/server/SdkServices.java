package org.jetlinks.sdk.server;

public interface SdkServices {

    String deviceService = "deviceService";

    /**
     * @see org.jetlinks.sdk.server.file.UploadFileCommand
     */
    String fileService = "fileService";

    /**
     * @see org.jetlinks.sdk.server.media.ProxyMediaStreamCommand
     * @see org.jetlinks.sdk.server.media.StopProxyMediaStreamCommand
     *
     */
    String mediaService = "mediaService";

    /**
     * 认证服务
     * @see org.jetlinks.sdk.server.auth.cmd.GetDimensionUserBindCommand
     * @see org.jetlinks.sdk.server.auth.cmd.GetUserDimensionsCommand
     */
    String authService = "authService";

    /**
     * @see org.jetlinks.sdk.server.ai.AiCommandSupports
     * @see org.jetlinks.sdk.server.ai.cv.PushMediaStreamCommand
     * @see org.jetlinks.sdk.server.ai.cv.ObjectDetectionCommand
     */
    String aiService = "aiService";

    /**
     * @see org.jetlinks.sdk.server.ai.llm.chat.ChatMessage
     */
    String aiAgentService = "aiAgentService";

}
