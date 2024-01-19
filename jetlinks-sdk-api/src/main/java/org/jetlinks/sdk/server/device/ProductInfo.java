package org.jetlinks.sdk.server.device;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.metadata.Jsonable;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * 产品信息.
 *
 * @author zhangji 2024/1/15
 */
@Getter
@Setter
public class ProductInfo implements Externalizable, Jsonable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "产品ID")
    private String id;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "所属产品分类ID")
    private String classifiedId;

    @Schema(description = "所属产品分类名称")
    private String classifiedName;

    @Schema(description = "图片地址")
    private String photoUrl;

    @Schema(description = "说明")
    private String describe;

    @Schema(description = "物模型定义")
    private String metadata;

    @Schema(description = "设备类型")
    private DeviceType deviceType;

    @Schema(description = "设备接入方式ID")
    private String accessId;

    @Schema(description = "设备接入方式")
    private String accessProvider;

    @Schema(description = "设备接入方式名称")
    private String accessName;

    @Schema(description = "创建者ID")
    private String creatorId;

    @Schema(description = "创建者时间")
    private Long createTime;

    @Schema(description = "产品状态 1正常,0禁用")
    private Byte state;

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = Jsonable.super.toJson();
        if (deviceType != null) {
            jsonObject.put("deviceType", deviceType.name());
        }
        return jsonObject;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(id, out);
        SerializeUtils.writeNullableUTF(name, out);
        SerializeUtils.writeNullableUTF(classifiedId, out);
        SerializeUtils.writeNullableUTF(classifiedName, out);
        SerializeUtils.writeNullableUTF(photoUrl, out);
        SerializeUtils.writeNullableUTF(describe, out);
        SerializeUtils.writeNullableUTF(metadata, out);
        SerializeUtils.writeNullableUTF(accessId, out);
        SerializeUtils.writeNullableUTF(accessProvider, out);
        SerializeUtils.writeNullableUTF(accessName, out);
        SerializeUtils.writeNullableUTF(creatorId, out);
        SerializeUtils.writeObject(createTime, out);
        out.writeByte(state);
        out.writeByte(deviceType == null ? -1 : deviceType.ordinal());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = SerializeUtils.readNullableUTF(in);
        name = SerializeUtils.readNullableUTF(in);
        classifiedId = SerializeUtils.readNullableUTF(in);
        classifiedName = SerializeUtils.readNullableUTF(in);
        photoUrl = SerializeUtils.readNullableUTF(in);
        describe = SerializeUtils.readNullableUTF(in);
        metadata = SerializeUtils.readNullableUTF(in);
        accessId = SerializeUtils.readNullableUTF(in);
        accessProvider = SerializeUtils.readNullableUTF(in);
        accessName = SerializeUtils.readNullableUTF(in);
        creatorId = SerializeUtils.readNullableUTF(in);
        createTime = (Long) SerializeUtils.readObject(in);
        state = in.readByte();
        byte deviceType = in.readByte();
        if (deviceType >= 0) {
            this.deviceType = DeviceType.values()[deviceType];
        }
    }
}
