package org.jetlinks.sdk.server.device;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.core.DefaultExtendable;
import org.jetlinks.core.metadata.Jsonable;

import java.io.*;
import java.util.Map;

@Getter
@Setter
public class DeviceInfo extends DefaultExtendable implements Serializable, Jsonable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "设备ID")
    private String id;

    @Schema(title = "设备名称")
    private String name;

    @Schema(title = "产品ID")
    private String productId;

    @Schema(title = "产品名称")
    private String productName;

    @Schema(title = "图片地址")
    private String photoUrl;

    @Schema(title = "配置信息")
    private Map<String, Object> configuration;

    @Schema(title = "创建人ID")
    private String creatorId;

    @Schema(title = "创建时间")
    private Long createTime;

    @Schema(title = "设备状态")
    private DeviceState state;

    @Schema(title = "父设备ID")
    private String parentId;

    @Schema(title = "描述")
    private String describe;

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = Jsonable.super.toJson();
        if (state != null) {
            jsonObject.put("state", state.name());
        }
        return jsonObject;
    }
}
