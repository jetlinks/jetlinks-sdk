package org.jetlinks.sdk.server.notify.cmd;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

@Getter
@Setter
public class NotifyResult implements Externalizable {

    public static final int STATE_SUCCESS = 0;
    public static final int STATE_QUEUED = 1;

    public static NotifyResult ok() {
        return of(STATE_SUCCESS);
    }

    public static NotifyResult of(int state) {
        NotifyResult result = new NotifyResult();
        result.state = state;
        return result;
    }

    private int state;

    /**
     * 通知的发送ID,后续回执等逻辑将使用此ID进行关联
     */
    private String callId;

    /**
     * 其他自定义参数
     */
    private Map<String, Object> others;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(state);
        SerializeUtils.writeNullableUTF(callId, out);
        SerializeUtils.writeKeyValue(others, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        state = in.read();
        callId = SerializeUtils.readNullableUTF(in);
        others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
    }
}
