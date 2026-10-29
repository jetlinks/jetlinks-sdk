package org.jetlinks.sdk.server.auth.cmd;

import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.auth.DimensionUserBinding;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import org.jetlinks.sdk.server.utils.ObjectMappers;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 通过 authService 增量绑定明确的用户及维度实例，保留关系属性。
 * 返回成功处理的绑定项数，不代表新插入行数；失败传播，不自动重试。
 * @since 1.1.1
 */
public class BindDimensionUsersCommand extends AbstractCommand<Mono<Integer>, BindDimensionUsersCommand> {
    private static final long serialVersionUID = 1L;

    public List<DimensionUserBinding> getBindings() {
        return ConverterUtils.convertToList(readable().get("bindings"),
            value -> ObjectMappers.JSON_MAPPER.convertValue(value, DimensionUserBinding.class));
    }

    public BindDimensionUsersCommand setBindings(List<DimensionUserBinding> bindings) {
        return with("bindings", bindings);
    }
}
