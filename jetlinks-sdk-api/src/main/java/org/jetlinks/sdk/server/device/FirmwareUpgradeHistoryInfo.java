package org.jetlinks.sdk.server.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * 固件升级记录.
 *
 * @author zhangji 2024/9/29
 * @since 2.3
 */
@Getter
@Setter
public class FirmwareUpgradeHistoryInfo implements Externalizable {

    private static final long serialVersionUID = -2316792031470364965L;

    @Schema(description = "ID")
    private String id;

    @Schema(description = "设备ID")
    private String deviceId;

    @Schema(description = "设备名称")
    private String deviceName;

    @Schema(description = "产品ID")
    private String productId;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "固件ID")
    private String firmwareId;

    @Schema(description = "固件版本")
    private String version;

    @Schema(description = "固件版本序号")
    private Integer versionOrder;

    @Schema(description = "升级任务ID")
    private String taskId;

    @Schema(description = "升级任务名称")
    private String taskName;

    @Schema(description = "升级方式")
    private FirmwareUpgradeMode mode;

    @Schema(description = "服务节点ID")
    private String serverId;

    @Schema(description = "创建时间(只读)")
    private Long createTime;

    @Schema(description = "升级时间")
    private Long upgradeTime;

    @Schema(description = "完成时间")
    private Long completeTime;

    @Schema(description = "升级超时时间")
    private Long timeoutSeconds;

    @Schema(description = "响应超时时间")
    private Long responseTimeoutSeconds;

    @Schema(description = "升级进度")
    private Integer progress;

    @Schema(description = "升级状态")
    private FirmwareUpgradeState state;

    //失败原因
    @Schema(description = "失败原因")
    private String errorReason;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(id, out);
        SerializeUtils.writeNullableUTF(deviceId, out);
        SerializeUtils.writeNullableUTF(deviceName, out);
        SerializeUtils.writeNullableUTF(productId, out);
        SerializeUtils.writeNullableUTF(productName, out);
        SerializeUtils.writeNullableUTF(firmwareId, out);
        SerializeUtils.writeNullableUTF(version, out);
        SerializeUtils.writeObject(versionOrder, out);
        SerializeUtils.writeNullableUTF(taskId, out);
        SerializeUtils.writeNullableUTF(taskName, out);
        SerializeUtils.writeObject(mode, out);
        SerializeUtils.writeNullableUTF(serverId, out);
        SerializeUtils.writeObject(createTime, out);
        SerializeUtils.writeObject(upgradeTime, out);
        SerializeUtils.writeObject(completeTime, out);
        SerializeUtils.writeObject(timeoutSeconds, out);
        SerializeUtils.writeObject(responseTimeoutSeconds, out);
        SerializeUtils.writeObject(progress, out);
        SerializeUtils.writeObject(state, out);
        SerializeUtils.writeNullableUTF(errorReason, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = SerializeUtils.readNullableUTF(in);
        deviceId = SerializeUtils.readNullableUTF(in);
        deviceName = SerializeUtils.readNullableUTF(in);
        productId = SerializeUtils.readNullableUTF(in);
        productName = SerializeUtils.readNullableUTF(in);
        firmwareId = SerializeUtils.readNullableUTF(in);
        version = SerializeUtils.readNullableUTF(in);
        versionOrder = (Integer) SerializeUtils.readObject(in);
        taskId = SerializeUtils.readNullableUTF(in);
        taskName = SerializeUtils.readNullableUTF(in);
        mode = (FirmwareUpgradeMode) SerializeUtils.readObject(in);
        serverId = SerializeUtils.readNullableUTF(in);
        createTime = (Long) SerializeUtils.readObject(in);
        upgradeTime = (Long) SerializeUtils.readObject(in);
        completeTime = (Long) SerializeUtils.readObject(in);
        timeoutSeconds = (Long) SerializeUtils.readObject(in);
        responseTimeoutSeconds = (Long) SerializeUtils.readObject(in);
        progress = (Integer) SerializeUtils.readObject(in);
        state = (FirmwareUpgradeState) SerializeUtils.readObject(in);
        errorReason = SerializeUtils.readNullableUTF(in);
    }
}
