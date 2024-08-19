package org.jetlinks.sdk.server.media.transcode;

import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Mono;

public abstract class TranscodeCommand<Self extends TranscodeCommand<Self>> extends AbstractCommand<Mono<Void>, Self> {

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


}
