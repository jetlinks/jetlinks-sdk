package org.jetlinks.sdk.server.media.transcode;

import io.netty.buffer.ByteBuf;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.AbstractStreamCommand;
import org.jetlinks.sdk.server.utils.CastUtils;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Mono;

public abstract class StreamingTranscodeCommand<Self extends StreamingTranscodeCommand<Self>>
    extends AbstractStreamCommand<ByteBuf, ByteBuf, Self> {

    public boolean isIgnoreResult() {
        return CastUtils.castBoolean(
            readable()
                .getOrDefault("ignoreResult", false));
    }

    public Self setIgnoreResult(boolean ignoreResult) {
        return with("ignoreResult", ignoreResult);
    }

    public String getSourceFormat() {
        return getOrNull("sourceFormat", String.class);
    }

    public Self setSourceFormat(String sourceFormat) {
        return with("sourceFormat", sourceFormat);
    }

    public String getTargetFormat() {
        return getOrNull("targetFormat", String.class);
    }

    public Self setTargetFormat(String targetFormat) {
        return with("targetFormat", targetFormat);
    }

    public String getTarget() {
        return getOrNull("target", String.class);
    }

    public Self setTarget(String target) {
        return with("target", target);
    }

    @Override
    public ByteBuf convertStreamValue(Object value) {
        return ConverterUtils.convertNettyBuffer(value);
    }

    @Override
    public Object createResponseData(Object value) {
        return ConverterUtils.convertNettyBuffer(value);
    }
}
