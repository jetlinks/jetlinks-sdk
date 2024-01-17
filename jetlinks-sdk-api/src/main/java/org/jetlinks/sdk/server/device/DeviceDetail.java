package org.jetlinks.sdk.server.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * 设备详情.
 *
 * @author zhangji 2024/1/15
 */
@Getter
@Setter
public class DeviceDetail implements Serializable {
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

}
