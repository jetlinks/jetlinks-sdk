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
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 设备详情.
 *
 * @author zhangji 2024/1/15
 */
@Getter
@Setter
public class DeviceDetail implements Externalizable, Jsonable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "设备ID")
    private String id;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "产品图片地址")
    private String productPhotoUrl;

    @Schema(description = "设备图片地址")
    private String devicePhotoUrl;

    @Schema(description = "消息协议ID")
    private String protocol;

    @Schema(description = "消息协议名称")
    private String protocolName;

    @Schema(description = "通信协议")
    private String transport;

    @Schema(description = "产品ID")
    private String productId;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "设备类型")
    private DeviceType deviceType;

    @Schema(description = "设备状态")
    private DeviceState state;

    @Schema(description = "ip地址")
    private String address;

    @Schema(description = "上线时间")
    private long onlineTime;

    @Schema(description = "离线时间")
    private long offlineTime;

    @Schema(description = "创建时间")
    private long createTime;

    @Schema(description = "激活时间")
    private long registerTime;

    @Schema(description = "物模型")
    private String metadata;

    @Schema(description = "产品物模型")
    private String productMetadata;

    @Schema(description = "是否为独立物模型")
    private boolean independentMetadata;

    @Schema(description = "配置信息")
    private Map<String, Object> configuration;

    @Schema(description = "设备自己的配置信息")
    private Map<String, Object> deviceConfiguration;

    @Schema(description = "已生效的配置信息")
    private Map<String, Object> cachedConfiguration;

    @Schema(description = "是否为单独的配置,false表示部分配置信息继承自产品.")
    private boolean aloneConfiguration;

    @Schema(description = "父设备ID")
    private String parentId;

    @Schema(description = "当前连接到的服务ID")
    private String connectServerId;

    @Schema(description = "设备描述")
    private String description;

    @Schema(description = "设备接入方式ID")
    private String accessId;

    @Schema(description = "设备接入方式")
    private String accessProvider;

    @Schema(description = "设备接入方式名称")
    private String accessName;

    @Schema(description = "产品所属分类ID")
    private String classifiedId;

    @Schema(description = "产品所属分类名称")
    private String classifiedName;

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = Jsonable.super.toJson();
        if (state != null) {
            jsonObject.put("state", state.name());
        }
        if (deviceType != null) {
            jsonObject.put("deviceType", deviceType.name());
        }
        return jsonObject;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(id, out);
        SerializeUtils.writeNullableUTF(name, out);
        SerializeUtils.writeNullableUTF(productPhotoUrl, out);
        SerializeUtils.writeNullableUTF(devicePhotoUrl, out);
        SerializeUtils.writeNullableUTF(protocol, out);
        SerializeUtils.writeNullableUTF(protocolName, out);
        SerializeUtils.writeNullableUTF(transport, out);
        SerializeUtils.writeNullableUTF(productId, out);
        SerializeUtils.writeNullableUTF(productName, out);
        SerializeUtils.writeNullableUTF(address, out);
        SerializeUtils.writeObject(onlineTime, out);
        SerializeUtils.writeObject(offlineTime, out);
        SerializeUtils.writeObject(createTime, out);
        SerializeUtils.writeObject(registerTime, out);
        SerializeUtils.writeNullableUTF(metadata, out);
        SerializeUtils.writeNullableUTF(productMetadata, out);
        SerializeUtils.writeObject(independentMetadata, out);
        SerializeUtils.writeKeyValue(configuration, out);
        SerializeUtils.writeKeyValue(deviceConfiguration, out);
        SerializeUtils.writeKeyValue(cachedConfiguration, out);
        SerializeUtils.writeObject(aloneConfiguration, out);
        SerializeUtils.writeNullableUTF(parentId, out);
        SerializeUtils.writeNullableUTF(connectServerId, out);
        SerializeUtils.writeNullableUTF(description, out);
        SerializeUtils.writeNullableUTF(accessId, out);
        SerializeUtils.writeNullableUTF(accessProvider, out);
        SerializeUtils.writeNullableUTF(accessName, out);
        SerializeUtils.writeNullableUTF(classifiedId, out);
        SerializeUtils.writeNullableUTF(classifiedName, out);

        out.writeByte(state == null ? -1 : state.ordinal());
        out.writeByte(deviceType == null ? -1 : deviceType.ordinal());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = SerializeUtils.readNullableUTF(in);
        name = SerializeUtils.readNullableUTF(in);
        productPhotoUrl = SerializeUtils.readNullableUTF(in);
        devicePhotoUrl = SerializeUtils.readNullableUTF(in);
        protocol = SerializeUtils.readNullableUTF(in);
        protocolName = SerializeUtils.readNullableUTF(in);
        transport = SerializeUtils.readNullableUTF(in);
        productId = SerializeUtils.readNullableUTF(in);
        productName = SerializeUtils.readNullableUTF(in);
        address = SerializeUtils.readNullableUTF(in);
        onlineTime = (Long) SerializeUtils.readObject(in);
        offlineTime = (Long) SerializeUtils.readObject(in);
        createTime = (Long) SerializeUtils.readObject(in);
        registerTime = (Long) SerializeUtils.readObject(in);
        metadata = SerializeUtils.readNullableUTF(in);
        productMetadata = SerializeUtils.readNullableUTF(in);
        independentMetadata = (Boolean) SerializeUtils.readObject(in);
        configuration = SerializeUtils.readMap(in, LinkedHashMap::new);
        deviceConfiguration = SerializeUtils.readMap(in, LinkedHashMap::new);
        cachedConfiguration = SerializeUtils.readMap(in, LinkedHashMap::new);
        aloneConfiguration = (Boolean) SerializeUtils.readObject(in);
        parentId = SerializeUtils.readNullableUTF(in);
        connectServerId = SerializeUtils.readNullableUTF(in);
        description = SerializeUtils.readNullableUTF(in);
        accessId = SerializeUtils.readNullableUTF(in);
        accessProvider = SerializeUtils.readNullableUTF(in);
        accessName = SerializeUtils.readNullableUTF(in);
        classifiedId = SerializeUtils.readNullableUTF(in);
        classifiedName = SerializeUtils.readNullableUTF(in);
        byte state = in.readByte();
        if (state >= 0) {
            this.state = DeviceState.values()[state];
        }
        byte deviceType = in.readByte();
        if (deviceType >= 0) {
            this.deviceType = DeviceType.values()[deviceType];
        }
    }
}
