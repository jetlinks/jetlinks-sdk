package org.jetlinks.sdk.server.media;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MediaStreamInfo extends MediaInfo {

    //rtp推流时的ssrc值
    @Nullable
    private String ssrc;

    //上下文信息,在停止推流时需要传递这些信息.
    private Map<String, Object> others;

    public void withOther(String key, Object value) {
        if (others == null) {
            others = new HashMap<>();
        }
        others.put(key, value);
    }
}
