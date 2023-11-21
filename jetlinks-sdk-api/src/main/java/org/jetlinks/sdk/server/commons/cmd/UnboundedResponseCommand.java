package org.jetlinks.sdk.server.commons.cmd;

import org.jetlinks.core.command.Command;
import reactor.core.publisher.Flux;

/**
 * 无界响应命令,实现该接口,表示命令将返回一个无界流,此命令将只允许使用websocket或者sse方式执行.
 *
 * @param <T> 数据类型
 * @author zhouhao
 * @since 2.1
 */
public interface UnboundedResponseCommand<T> extends Command<Flux<T>> {
}
