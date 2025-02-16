package org.jetlinks.sdk.server.ai;

import org.jetlinks.core.command.Command;
import reactor.core.publisher.Flux;

/**
 * AI命令,实现此接口,标记命令为AI相关的命令
 *
 * @param <R> 命令返回类型
 * @see AiDomain
 */
public interface AiCommand<R extends AiOutput<R>> extends Command<Flux<R>> {

}
