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

}
