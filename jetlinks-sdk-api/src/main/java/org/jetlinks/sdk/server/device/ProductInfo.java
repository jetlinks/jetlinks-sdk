package org.jetlinks.sdk.server.device;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.core.DefaultExtendable;
import org.jetlinks.core.metadata.Jsonable;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.*;

/**
 * 产品信息.
 *
 * @author zhangji 2024/1/15
 */
@Getter
@Setter
public class ProductInfo extends DefaultExtendable implements Serializable, Jsonable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "产品ID")
    private String id;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "所属产品分类ID")
    private String classifiedId;

    @Schema(description = "所属产品分类名称")
    private String classifiedName;

    @Schema(description = "图片地址")
    private String photoUrl;

    @Schema(description = "说明")
    private String describe;

    @Schema(description = "物模型定义")
    private String metadata;

    @Schema(description = "设备类型")
    private DeviceType deviceType;

    @Schema(description = "设备接入方式ID")
    private String accessId;

    @Schema(description = "设备接入方式")
    private String accessProvider;

    @Schema(description = "设备接入方式名称")
    private String accessName;

    @Schema(description = "创建者ID")
    private String creatorId;

    @Schema(description = "创建者时间")
    private Long createTime;

    @Schema(description = "产品状态 1正常,0禁用")
    private Byte state;

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = Jsonable.super.toJson();
        if (deviceType != null) {
            jsonObject.put("deviceType", deviceType.name());
        }
        return jsonObject;
    }

}
