package org.jetlinks.sdk.server.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;
import org.jetlinks.sdk.server.ui.field.annotation.field.DateTime;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

@Getter
@Setter
public class DeviceProperty implements Externalizable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "设备ID")
    private String deviceId;

    @Schema(description = "属性ID")
    private String property;

    @Schema(description = "属性名")
    private String propertyName;

    @Schema(description = "状态")
    private String state;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "属性值")
    private Object value;

    @Schema(description = "数字值")
    private Object numberValue;

    @Schema(description = "格式化后的值")
    private Object formatValue;

    @Schema(description = "单位")
    private String unit;

    @DateTime(timestamp = true)
    @Schema(description = "时间戳")
    private long timestamp;

    @DateTime(timestamp = true)
    @Schema(description = "创建时间")
    private long createTime;


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(deviceId, out);
        SerializeUtils.writeNullableUTF(property, out);
        SerializeUtils.writeNullableUTF(propertyName, out);
        SerializeUtils.writeNullableUTF(state, out);
        SerializeUtils.writeNullableUTF(type, out);
        SerializeUtils.writeObject(value, out);
        SerializeUtils.writeObject(numberValue, out);
        SerializeUtils.writeObject(formatValue, out);
        SerializeUtils.writeNullableUTF(unit, out);
        out.writeLong(timestamp);
        out.writeLong(createTime);

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        deviceId = SerializeUtils.readNullableUTF(in);
        property = SerializeUtils.readNullableUTF(in);
        propertyName = SerializeUtils.readNullableUTF(in);
        state = SerializeUtils.readNullableUTF(in);
        type = SerializeUtils.readNullableUTF(in);
        value = SerializeUtils.readObject(in);
        numberValue = SerializeUtils.readObject(in);
        formatValue = SerializeUtils.readObject(in);
        unit = SerializeUtils.readNullableUTF(in);
        timestamp = in.readLong();
        createTime = in.readLong();
    }
}
