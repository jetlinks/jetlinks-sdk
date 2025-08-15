package org.jetlinks.sdk.server.media.transcode;

import io.netty.buffer.ByteBuf;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.utils.CastUtils;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class TranscodeCommand<Self extends TranscodeCommand<Self>> extends AbstractCommand<Flux<ByteBuf>, Self> {

    public String getSource() {
        return getOrNull("source", String.class);
    }

    public Self setSource(String source) {
        return with("source", source);
    }

    public String getTarget() {
        return getOrNull("target", String.class);
    }

    public Self setTarget(String target) {
        return with("target", target);
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

    public boolean isIgnoreAudio() {
        return CastUtils.castBoolean(
            readable()
                .getOrDefault("ignoreAudio", false));
    }

    public Self setIgnoreAudio(boolean onlyAudio) {
        return with("ignoreAudio", onlyAudio);
    }


    public boolean isOnlyAudio() {
        return CastUtils.castBoolean(
            readable()
                .getOrDefault("onlyAudio", false));
    }

    public Self setOnlyAudio(boolean onlyAudio) {
        return with("onlyAudio", onlyAudio);
    }

    public boolean isAwait() {
        return CastUtils.castBoolean(
            readable()
                .getOrDefault("await", false));
    }

    public Self setAwait(boolean await) {
        return with("await", await);
    }

    /**
     * @see AudioCodecs
     */
    public String getAudioCodec() {
        return getOrNull("audioCodec", String.class);
    }

    /**
     * @see AudioCodecs
     */
    public Self setAudioCodec(String codec) {
        return with("audioCodec", codec);
    }

    /**
     * @see VideoCodecs
     */
    public String getVideoCodec() {
        return getOrNull("videoCodec", String.class);
    }

    /**
     * @see VideoCodecs
     */
    public Self setVideoCodec(String codec) {
        return with("videoCodec", codec);
    }


    @Override
    public Object createResponseData(Object value) {
        return ConverterUtils.convertNettyBuffer(value);
    }
}
