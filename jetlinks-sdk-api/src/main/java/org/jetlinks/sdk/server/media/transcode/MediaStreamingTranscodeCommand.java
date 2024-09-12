package org.jetlinks.sdk.server.media.transcode;

import io.netty.buffer.ByteBuf;
import org.jetlinks.core.command.StreamCommand;
import org.jetlinks.sdk.server.utils.CastUtils;
import reactor.core.publisher.Flux;

import javax.annotation.Nonnull;

public class MediaStreamingTranscodeCommand extends TranscodeCommand<MediaStreamingTranscodeCommand> implements StreamCommand<ByteBuf, ByteBuf> {


    private Flux<ByteBuf> stream;

    @Nonnull
    @Override
    public Flux<ByteBuf> stream() {
        return stream == null ? Flux.empty() : stream;
    }

    @Override
    public void withStream(@Nonnull Flux<ByteBuf> stream) {
        this.stream = stream;
    }



}
