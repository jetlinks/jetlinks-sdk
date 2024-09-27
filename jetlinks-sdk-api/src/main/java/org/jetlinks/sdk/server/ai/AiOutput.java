package org.jetlinks.sdk.server.ai;

import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.metadata.Jsonable;

import java.io.Externalizable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gyl
 * @since 2.3
 */
public interface AiOutput extends Externalizable, Jsonable {

    /**
     * 获取数据ID
     */
    String getId();

    long getTimestamp();

    default List<Map<String, Object>> toLightWeighMap() {
        Map<String, Object> copy = FastBeanCopier.copy(this, new HashMap<>());
        return Collections.singletonList(copy);
    }

    default List<Map<String, Object>> flat() {
        return toLightWeighMap();
    }




}
