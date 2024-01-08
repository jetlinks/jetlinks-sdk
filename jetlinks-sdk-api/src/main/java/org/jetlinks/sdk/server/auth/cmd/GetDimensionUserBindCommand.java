package org.jetlinks.sdk.server.auth.cmd;

import org.hswebframework.web.authorization.Dimension;
import org.hswebframework.web.authorization.DimensionType;
import org.hswebframework.web.authorization.dimension.DimensionUserBind;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.utils.CastUtils;
import reactor.core.publisher.Flux;

import java.util.Collection;

/**
 * 获取用户的维度绑定信息,如获取哪些用户绑定了
 *
 * @author zhouhao
 * @see org.hswebframework.web.authorization.Dimension
 * @see org.hswebframework.web.authorization.Authentication
 * @since 1.0
 */
public class GetDimensionUserBindCommand extends AbstractCommand<Flux<DimensionUserBind>, GetDimensionUserBindCommand> {

    /**
     * @return 维度类型 ,如 org,role.
     * @see DimensionType#getId()
     */
    public String getDimensionType() {
        return (String) readable().get("dimensionType");
    }

    /**
     * @return 维度ID
     * @see Dimension#getId()
     */
    public Collection<String> getDimensionId() {
        return CastUtils.castCollection(readable().get("dimensionId"));
    }

    public GetDimensionUserBindCommand withDimensionType(String dimensionType) {
        return with("dimensionType", dimensionType);
    }

    public GetDimensionUserBindCommand withDimensionId(String dimensionId) {
        return with("dimensionId", dimensionId);
    }


}
