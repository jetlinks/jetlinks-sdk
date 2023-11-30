package org.jetlinks.sdk.server.event;

import org.jetlinks.sdk.server.commons.cmd.SubscribeCommand;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author gyl
 * @since 2.2
 */
public abstract class SubscribeCommandHandler<T> {

    protected List<Function<T, Mono<T>>> callbacks = new ArrayList<>();

    public <C extends SubscribeCommand<T, C>> Disposable addCallback(C cmd, Function<T, Mono<T>> callback) {
        callbacks.add(callback);
        return () -> callbacks.remove(callback);
    }


}
