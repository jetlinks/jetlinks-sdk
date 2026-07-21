package org.jetlinks.sdk.server.media.service;

import io.netty.buffer.ByteBuf;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractStreamCommand;
import org.jetlinks.core.utils.ConverterUtils;

import java.util.Map;

public class AiProcessImageCommand extends AbstractStreamCommand<ByteBuf, Map<String, Object>, AiProcessImageCommand> {

    public static final String FILE_URL_KEY = "fileUrl";

    @Schema(title = "文件id")
    public String getFileUrl() {
        return getOrNull(FILE_URL_KEY, String.class);
    }

    public AiProcessImageCommand setFileUrl(String fileUrl) {
        return with(FILE_URL_KEY, fileUrl);
    }

    @Override
    public ByteBuf convertStreamValue(Object value) {
        return ConverterUtils.convertNettyBuffer(value);
    }
}
