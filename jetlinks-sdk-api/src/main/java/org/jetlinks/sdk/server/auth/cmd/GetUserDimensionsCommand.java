package org.jetlinks.sdk.server.auth.cmd;

import org.hswebframework.web.authorization.dimension.DimensionUserDetail;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.utils.CastUtils;
import reactor.core.publisher.Flux;

import java.util.Collection;

/**
 * 获取用户的维度信息,如获取用户角色,组织等信息
 *
 * @author zhouhao
 * @since 1.0
 */
public class GetUserDimensionsCommand extends AbstractCommand<Flux<DimensionUserDetail>, GetUserDimensionsCommand> {

    @SuppressWarnings("all")
    public Collection<String> getUserId() {
        return CastUtils.castCollection(readable().get("userId"));
    }

    public GetUserDimensionsCommand withUserId(Collection<String> userId) {
        return with("userId", userId);
    }

}
