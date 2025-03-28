package org.jetlinks.sdk.server.media;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        SerializeUtils.writeNullableUTF(ssrc,out);
        SerializeUtils.writeObject(others,out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        ssrc = SerializeUtils.readNullableUTF(in);
        others = (Map<String, Object>)SerializeUtils.readObject(in);
    }
}
