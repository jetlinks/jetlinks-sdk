package org.jetlinks.sdk.server.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 固件信息.
 *
 * @author zhangji 2024/9/29
 * @since 2.3
 */
@Getter
@Setter
public class FirmwareInfo implements Serializable {

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

    @Schema(description = "创建时间(只读)")
    private Long createTime;

    @Schema(description = "说明")
    private String description;

}