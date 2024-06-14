package org.jetlinks.sdk.server.media;

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

/**
 * 流媒体通道
 * @see org.jetlinks.sdk.server.commons.cmd.QueryListCommand
 * @see org.jetlinks.sdk.server.commons.cmd.QueryPagerCommand
 */
@Getter
@Setter
public class MediaChannel implements Externalizable {

    @Schema(description = "设备ID")
    private String deviceId;

    @Schema(description = "通道ID")
    private String channelId;

    @Schema(description = "通道名称")
    private String name;

    @Schema(description = "厂商")
    private String manufacturer;

    @Schema(description = "型号")
    private String model;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "其他拓展信息")
    private Map<String, Object> others;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(deviceId, out);
        SerializeUtils.writeNullableUTF(channelId, out);
        SerializeUtils.writeNullableUTF(name, out);
        SerializeUtils.writeNullableUTF(manufacturer, out);
        SerializeUtils.writeNullableUTF(model, out);
        SerializeUtils.writeNullableUTF(address, out);
        SerializeUtils.writeKeyValue(others, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        deviceId = SerializeUtils.readNullableUTF(in);
        channelId = SerializeUtils.readNullableUTF(in);
        name = SerializeUtils.readNullableUTF(in);
        manufacturer = SerializeUtils.readNullableUTF(in);
        model = SerializeUtils.readNullableUTF(in);
        address = SerializeUtils.readNullableUTF(in);
        others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
    }
}
