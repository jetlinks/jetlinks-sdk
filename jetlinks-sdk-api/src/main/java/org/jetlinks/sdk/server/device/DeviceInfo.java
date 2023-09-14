package org.jetlinks.sdk.server.device;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class DeviceInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String productId;

    private String productName;

    private String photoUrl;

    private Map<String, Object> configuration;

    private String creatorId;

    private Long createTime;

    private DeviceState state;

}
