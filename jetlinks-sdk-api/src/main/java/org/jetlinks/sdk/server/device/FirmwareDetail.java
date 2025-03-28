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
public class FirmwareDetail extends FirmwareInfo {

    private static final long serialVersionUID = -8877936284895701414L;

    @Schema(description = "固件文件签名")
    private String sign;

    @Schema(description = "固件文件签名方式,如:MD5,SHA256")
    private String signMethod;

    @Schema(description = "固件文件大小")
    private Long size;

    @Schema(description = "其他拓展信息")
    private List<Property> properties;

    @Schema(description = "升级记录")
    private List<FirmwareUpgradeHistoryInfo> history;

    public FirmwareDetail with(List<FirmwareUpgradeHistoryInfo> history) {
        this.history = history;
        return this;
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
