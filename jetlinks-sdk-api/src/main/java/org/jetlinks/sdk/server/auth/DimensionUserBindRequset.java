package org.jetlinks.sdk.server.auth;

import lombok.*;
import org.hswebframework.web.bean.FastBeanCopier;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gyl
 * @since 2.2
 */
@Getter
@Setter
@Deprecated
public class DimensionUserBindRequset extends DimensionUserBindRequest {

    public DimensionUserBindRequset() {
    }

    public DimensionUserBindRequset(String type, String id) {
        super(type, id);
    }

    @SuppressWarnings("all")
    public static DimensionUserBindRequset of(Object object) {
        if (object instanceof DimensionUserBindRequset) {
            return ((DimensionUserBindRequset) object);
        }
        Map<String, Object> map;
        if (!(object instanceof Map)) {
            map = FastBeanCopier.copy(object, new HashMap<>());
        } else {
            map = (Map<String, Object>) object;
        }
        return new DimensionUserBindRequset(String.valueOf(map.get("type")), String.valueOf(map.get("id")));
    }
}
