package org.jetlinks.sdk.server.media.transcode;

import io.netty.buffer.ByteBuf;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.AbstractStreamCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class StreamingTranscodeCommand extends AbstractStreamCommand<ByteBuf, Flux<ByteBuf>, StreamingTranscodeCommand> {


    public String getSource() {
        return getOrNull("source", String.class);
    }

    public String getTarget() {
        return getOrNull("target", String.class);
    }

    public String getCodec() {
        return getOrNull("codec", String.class);
    }


}
