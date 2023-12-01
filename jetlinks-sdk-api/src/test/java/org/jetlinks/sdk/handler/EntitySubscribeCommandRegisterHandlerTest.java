package org.jetlinks.sdk.handler;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hswebframework.web.crud.events.EntitySavedEvent;
import org.jetlinks.core.command.AbstractCommandSupport;
import org.jetlinks.core.metadata.types.LongType;
import org.jetlinks.sdk.server.handler.EntitySubscribeCommandRegisterHandler;
import org.jetlinks.sdk.server.handler.EntitySubscribeType;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author gyl
 * @since 2.2
 */
@Slf4j
public class EntitySubscribeCommandRegisterHandlerTest {


    @SneakyThrows
    @Test
    void test() {
        TestCommandSupport support = new TestCommandSupport();
        support.init();
        Disposable subscribe = support
                .executeToFlux(EntitySubscribeType.saved.getId(), Collections.emptyMap())
                .subscribe(l -> {
                           },
                           err -> log.error("sub.err", err));
        Flux.interval(Duration.ofMillis(100))
            .doOnNext(i -> {
                if (i >= 10) {
                    subscribe.dispose();
                    assertEquals(10, support.getCounter().get());
                }
            })
            .flatMap(support::fireEvent)
            .subscribe(l -> {
                       },
                       err -> log.error("fireEvent.err", err));


        Thread.sleep(Duration.ofSeconds(3).toMillis());
        assertEquals(10, support.getCounter().get());
    }


    static class TestCommandSupport extends AbstractCommandSupport {
        public final EntitySubscribeCommandRegisterHandler handlers = new EntitySubscribeCommandRegisterHandler();
        @Getter
        public final AtomicInteger counter = new AtomicInteger(0);

        public void init() {
            handlers
                    .register(this::registerHandler,
                              LongType.GLOBAL,
                              this::log);
        }

        private Function<Object, Mono<Void>> log(Function<Object, Mono<Object>> callback) {
            return obj -> callback
                    .apply(obj)
                    .switchIfEmpty(Mono.fromRunnable(() -> log.info("empty")))
                    .doOnNext(l -> {
                        counter.incrementAndGet();
                        log.info("sub:{}", l);
                    })
                    .then();
        }

        public Mono<Void> fireEvent(Long data) {
            return handlers.handle(new EntitySavedEvent<>(Collections.singletonList(data), Long.class));
        }
    }
}
