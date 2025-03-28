package org.jetlinks.sdk.server.auth;

import lombok.*;
import org.hswebframework.web.bean.FastBeanCopier;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0.1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DimensionUserBindRequest implements Serializable {
    private String type;
    private String id;

    @SuppressWarnings("all")
    public static DimensionUserBindRequest of(Object object) {
        if (object instanceof DimensionUserBindRequest) {
            return ((DimensionUserBindRequest) object);
        }
        Map<String, Object> map;
        if (!(object instanceof Map)) {
            map = FastBeanCopier.copy(object, new HashMap<>());
        } else {
            map = (Map<String, Object>) object;
        }
        return new DimensionUserBindRequest(String.valueOf(map.get("type")), String.valueOf(map.get("id")));
    }
}
