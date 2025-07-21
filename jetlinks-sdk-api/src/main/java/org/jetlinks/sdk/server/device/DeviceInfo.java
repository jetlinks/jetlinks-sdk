package org.jetlinks.sdk.server.device;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.metadata.Jsonable;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class DeviceInfo implements Externalizable, Jsonable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "设备ID")
    private String id;

    @Schema(title = "设备名称")
    private String name;

    @Schema(title = "产品ID")
    private String productId;

    @Schema(title = "产品名称")
    private String productName;

    @Schema(title = "图片地址")
    private String photoUrl;

    @Schema(title = "配置信息")
    private Map<String, Object> configuration;

    @Schema(title = "创建人ID")
    private String creatorId;

    @Schema(title = "创建时间")
    private Long createTime;

    @Schema(title = "设备状态")
    private DeviceState state;

    @Schema(title = "父设备ID")
    private String parentId;

    @Schema(title = "描述")
    private String describe;

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = Jsonable.super.toJson();
        if (state != null) {
            jsonObject.put("state", state.name());
        }
        return jsonObject;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(id, out);
        SerializeUtils.writeNullableUTF(name, out);
        SerializeUtils.writeNullableUTF(productId, out);
        SerializeUtils.writeNullableUTF(productName, out);
        SerializeUtils.writeNullableUTF(photoUrl, out);
        SerializeUtils.writeKeyValue(configuration, out);
        SerializeUtils.writeNullableUTF(creatorId, out);
        SerializeUtils.writeObject(createTime, out);
        SerializeUtils.writeNullableUTF(parentId, out);
        out.writeByte(state == null ? -1 : state.ordinal());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = SerializeUtils.readNullableUTF(in);
        name = SerializeUtils.readNullableUTF(in);
        productId = SerializeUtils.readNullableUTF(in);
        productName = SerializeUtils.readNullableUTF(in);
        photoUrl = SerializeUtils.readNullableUTF(in);
        configuration = SerializeUtils.readMap(in, LinkedHashMap::new);
        creatorId = SerializeUtils.readNullableUTF(in);
        createTime = (Long) SerializeUtils.readObject(in);
        parentId = SerializeUtils.readNullableUTF(in);
        byte state = in.readByte();
        if (state >= 0) {
            this.state = DeviceState.values()[state];
        }
    }
}
