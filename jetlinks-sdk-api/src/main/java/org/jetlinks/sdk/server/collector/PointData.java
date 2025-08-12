package org.jetlinks.sdk.server.collector;

import com.google.common.collect.Maps;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.binary.Hex;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointData implements Externalizable {
    private static final long serialVersionUID = 1L;

    /**
     * 点位ID
     */
    @Nonnull
    private String pointId;

    /**
     * 原始数据,与{@link  PointData#getParseData()}不能同时为null
     */
    private byte[] nativeData;

    /**
     * 解析后的数据,不同的点位格式不同.通常有基本数据类型和Map组成.
     * 比如在opcUa等场景,值通常为点位对应的值. 在modbus等场景是根据{@link org.jetlinks.pro.collector.codec.PropertyCodec}
     */
    private Object parseData;

    /**
     * 点位状态,由不同点位采集实现来定义
     */
    private String state;

    private long timestamp = System.currentTimeMillis();

    // 拓展配置
    private Map<String, Object> others;

    @Override
    public String toString() {
        String val = parseData == null ? Hex.encodeHexString(nativeData) : String.valueOf(parseData);
        return pointId+":" + ( state == null ? val : val + " (" + state + ")");
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

        out.writeUTF(pointId);

        int size = nativeData == null ? 0 : nativeData.length;
        out.writeInt(size);
        if (nativeData != null) {
            out.write(nativeData);
        }
        SerializeUtils.writeObject(parseData, out);
        SerializeUtils.writeObject(state, out);
        out.writeLong(timestamp);
        SerializeUtils.writeKeyValue(others, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        pointId = in.readUTF();

        int size = in.readInt();
        if (size > 0) {
            nativeData = new byte[size];
            in.readFully(nativeData);
        }
        parseData = SerializeUtils.readObject(in);
        state = (String) SerializeUtils.readObject(in);
        timestamp = in.readLong();
        others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
    }
}
