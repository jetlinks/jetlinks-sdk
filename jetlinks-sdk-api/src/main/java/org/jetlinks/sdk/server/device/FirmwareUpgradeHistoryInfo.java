package org.jetlinks.sdk.server.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.*;

/**
 * 固件升级记录.
 *
 * @author zhangji 2024/9/29
 * @since 2.3
 */
@Getter
@Setter
public class FirmwareUpgradeHistoryInfo implements Serializable {

    @Serial
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

}
