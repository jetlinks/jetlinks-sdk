package org.jetlinks.sdk.server.commons.cmd;

import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.metadata.FunctionMetadata;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.*;

/**
 * 订阅命令,用于订阅一个无界流.可指定回调在收到数据时会先执行回调的逻辑.
 * <p>
 * 如果回调返回错误,也将传递到事件源中.
 *
 * @param <T>    数据类型
 * @param <Self> Self
 * @see UnboundedResponseCommand
 * @see SubscribeCommand#createHandler(Supplier, BiFunction, Supplier)
 */
public abstract class SubscribeCommand<T, Self extends SubscribeCommand<T, Self>>
    extends AbstractCommand<Flux<T>, Self> implements UnboundedResponseCommand<T> {

    private transient Function<T, Mono<T>> callback;

    public Self withCallback(Function<T, Mono<T>> asyncCallback) {
        this.callback = asyncCallback;
        return castSelf();
    }


    public Function<T, Mono<T>> getCallback() {
        return callback == null ? Mono::just : callback;
    }


    public static <T, C extends SubscribeCommand<T, C>> CommandHandler<C, Flux<T>>
    createHandler(Supplier<FunctionMetadata> metadata,
                  BiFunction<C, Function<T, Mono<T>>, Disposable> handler,
                  Supplier<C> instance) {
        return CommandHandler
            .of(metadata, (cmd, support) -> {
                Function<T, Mono<T>> callback = cmd.getCallback();
                return Flux
                    .create(sink -> sink
                        .onDispose(
                            handler.apply(cmd, (val) -> callback.apply(val).doOnNext(sink::next))
                        ));

            }, instance);
    }

}
