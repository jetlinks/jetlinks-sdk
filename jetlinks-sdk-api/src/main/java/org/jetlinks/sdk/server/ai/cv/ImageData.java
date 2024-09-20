package org.jetlinks.sdk.server.ai.cv;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

@Setter
@Getter
public class ImageData implements Externalizable {

    @Schema(description = "图片id")
    private String id;

    @Schema(description = "图片数据")
    private ByteBuf data;

    @Schema(description = "其他信息")
    private Map<String, Object> others;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(id, out);
        SerializeUtils.writeKeyValue(others, out);
        SerializeUtils.writeObject(data, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = SerializeUtils.readNullableUTF(in);
        others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
        data = (ByteBuf) SerializeUtils.readObject(in);
    }
}
