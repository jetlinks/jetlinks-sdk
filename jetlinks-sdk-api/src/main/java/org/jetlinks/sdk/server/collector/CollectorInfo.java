package org.jetlinks.sdk.server.collector;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wangsheng
 */
@Getter
@Setter
public class CollectorInfo implements Serializable {
    private static final long serialVersionUID = -807130729452925273L;
    
    @Schema(description = "采集器ID")
    private String id;
    
    @Schema(description = "名称")
    private String name;
    
    @Schema(description = "说明")
    private String description;
    
    @Schema(description = "采集器提供商")
    private String provider;
    
    @Schema(description = "采集通道ID")
    private String channelId;
    
    @Schema(description = "采集通道名称")
    private String channelName;
    
    @Schema(description = "采集配置")
    private Map<String, Object> configuration;
    
    @Schema(description = "创建人ID")
    private String creatorId;
    
    @Schema(description = "创建时间")
    private Long createTime;
}
