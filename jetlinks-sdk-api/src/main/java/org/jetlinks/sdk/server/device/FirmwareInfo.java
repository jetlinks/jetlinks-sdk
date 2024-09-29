package org.jetlinks.sdk.server.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

/**
 * 固件信息详情.
 *
 * @author zhangji 2024/9/29
 * @since 2.3
 */
@Getter
@Setter
public class FirmwareInfo implements Externalizable {

    private static final long serialVersionUID = 1566508792298506679L;

    @Schema(description = "ID")
    private String id;

    @Schema(description = "产品ID")
    private String productId;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "固件名称")
    private String name;

    @Schema(description = "版本号")
    private String version;

    @Schema(description = "版本序号")
    private Integer versionOrder;

    @Schema(description = "固件文件地址")
    private String url;

    @Schema(description = "固件文件签名")
    private String sign;

    @Schema(description = "固件文件签名方式,如:MD5,SHA256")
    private String signMethod;

    @Schema(description = "固件文件大小")
    private Long size;

    @Schema(description = "创建时间(只读)")
    private Long createTime;

    @Schema(description = "其他拓展信息")
    private List<Property> properties;

    @Schema(description = "说明")
    private String description;

    @Schema(description = "升级记录")
    private List<FirmwareUpgradeHistoryInfo> history;

    public FirmwareInfo with(List<FirmwareUpgradeHistoryInfo> history) {
        this.history = history;
        return this;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(id, out);
        SerializeUtils.writeNullableUTF(productId, out);
        SerializeUtils.writeNullableUTF(productName, out);
        SerializeUtils.writeNullableUTF(name, out);
        SerializeUtils.writeNullableUTF(version, out);
        SerializeUtils.writeObject(versionOrder, out);
        SerializeUtils.writeNullableUTF(url, out);
        SerializeUtils.writeNullableUTF(sign, out);
        SerializeUtils.writeNullableUTF(signMethod, out);
        SerializeUtils.writeObject(size, out);
        SerializeUtils.writeObject(createTime, out);
        SerializeUtils.writeObject(properties, out);
        SerializeUtils.writeNullableUTF(description, out);
        SerializeUtils.writeObject(history, out);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = SerializeUtils.readNullableUTF(in);
        productId = SerializeUtils.readNullableUTF(in);
        productName = SerializeUtils.readNullableUTF(in);
        name = SerializeUtils.readNullableUTF(in);
        version = SerializeUtils.readNullableUTF(in);
        versionOrder = (Integer) SerializeUtils.readObject(in);
        url = SerializeUtils.readNullableUTF(in);
        sign = SerializeUtils.readNullableUTF(in);
        signMethod = SerializeUtils.readNullableUTF(in);
        size = (Long) SerializeUtils.readObject(in);
        createTime = (Long) SerializeUtils.readObject(in);
        properties = (List<Property>) SerializeUtils.readObject(in);
        description = SerializeUtils.readNullableUTF(in);
        history = (List<FirmwareUpgradeHistoryInfo>) SerializeUtils.readObject(in);
    }

    @Getter
    @Setter
    public static class Property implements Externalizable {
        private static final long serialVersionUID = -6849794470754667710L;

        private String id;

        private String name;

        private String value;

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            SerializeUtils.writeNullableUTF(id, out);
            SerializeUtils.writeNullableUTF(name, out);
            SerializeUtils.writeNullableUTF(value, out);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            id = SerializeUtils.readNullableUTF(in);
            name = SerializeUtils.readNullableUTF(in);
            value = SerializeUtils.readNullableUTF(in);
        }
    }

}