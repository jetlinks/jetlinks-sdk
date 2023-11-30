package org.jetlinks.sdk.server.handler;

/**
 * @author gyl
 * @since 2.2
 */

import lombok.extern.slf4j.Slf4j;
import org.jetlinks.sdk.server.commons.cmd.SubscribeCommand;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

@Slf4j
public abstract class SubscribeCommandHandler {
    protected final Map<Class<?>, List<Function<Object, Mono<Void>>>> callbacks = new ConcurrentHashMap<>();

    public <T, CMD extends SubscribeCommand<T, CMD>> Disposable addCallback(Class<T> eventClass,
                                                                            CMD cmd,
                                                                            Function<T, Mono<T>> callback) {
        Function<Object, Mono<Void>> function = obj -> callback.apply(eventClass.cast(obj)).then();
        callbacks.compute(eventClass, (k, list) -> {
            if (list == null) {
                list = new CopyOnWriteArrayList<>();
            }

            list.add(function);
            return list;
        });
        return () -> callbacks.computeIfPresent(eventClass, (k, list) -> {
            list.remove(function);
            return list;
        });
    }

    protected Mono<Void> handle(Object event) {
        return handle(event, callbacks.getOrDefault(event.getClass(), Collections.emptyList()));
    }

    protected static Mono<Void> handle(Object event, List<Function<Object, Mono<Void>>> functions) {
        return Flux
            .fromIterable(functions)
            .flatMap(callback -> callback
                .apply(event)
                .onErrorResume(err -> {
                    log.error("error.handle_event_fail", err);
                    return Mono.empty();
                }))
            .then();
    }
}