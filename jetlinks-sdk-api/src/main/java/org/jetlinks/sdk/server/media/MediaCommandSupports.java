package org.jetlinks.sdk.server.media;

public interface MediaCommandSupports {

    /**
     * 流媒体通道相关命令支持
     * mediaService:channel
     *
     * @see org.jetlinks.sdk.server.SdkServices#mediaService
     */
    String channel = "channel";


    /**
     * 流媒体录制相关命令支持
     * mediaService:record
     *
     * @see org.jetlinks.sdk.server.SdkServices#mediaService
     */
    String record = "record";

}
