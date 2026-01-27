package org.jetlinks.sdk.server.ai.cv;

import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class MediaSource implements Externalizable {

    @Schema(description = "来源标识")
    private String id;

    @Schema(description = "RTSP地址")
    private String rtsp;

    @Schema(description = "RTMP地址")
    private String rtmp;

    @Schema(description = "其他信息")
    private Map<String, Object> others;

    @Schema(description = "模型参数")
    private Map<String, Object> params;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(id, out);
        SerializeUtils.writeNullableUTF(rtsp, out);
        SerializeUtils.writeNullableUTF(rtmp, out);
        SerializeUtils.writeKeyValue(others, out);
        SerializeUtils.writeKeyValue(params, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = SerializeUtils.readNullableUTF(in);
        rtsp = SerializeUtils.readNullableUTF(in);
        rtmp = SerializeUtils.readNullableUTF(in);
        others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
        params = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
    }
}
