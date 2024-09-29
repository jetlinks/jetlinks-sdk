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

    /**
     * 获取为轻量数据，作用于统一存储等
     */
    default Map<String, Object> toLightWeighMap() {
        return FastBeanCopier.copy(this, new HashMap<>());
    }

    /**
     * 获取为平铺数据，作用于规则订阅等
     */
    default List<Map<String, Object>> flat() {
        return Collections.singletonList(toLightWeighMap());
    }

    /**
     * 获取为轻量平铺数据，作用于业务存储等
     */
    default List<Map<String, Object>> lightWeighFlat() {
        return flat();
    }


}
