package org.jetlinks.sdk.server.ai.cv.data;

import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import javax.annotation.Nullable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

/**
 * 目标检测对象关联的已确认身份。
 * <p>
 * 仅在身份已经通过匹配规则确认后设置，候选或存在歧义的匹配结果不应写入此对象。
 */
@Getter
@Setter
@Schema(title = "目标身份")
public class Identity implements Externalizable {

    public static final String ANNOTATIONS_KEY = "identity";

    /**
     * 身份解析配置标识。比如人脸库标识等
     * <p>
     * 对应配置定义身份对象类型、身份来源、匹配规则以及分数语义。
     */
    @Schema(title = "身份解析配置标识")
    private String profile;

    /**
     * 已识别业务对象ID。
     */
    @Schema(title = "身份对象ID")
    private String id;

    /**
     * 身份对象名称快照。
     */
    @Nullable
    @Schema(title = "身份对象名称")
    private String name;

    /**
     * 身份匹配分数。
     */
    @Nullable
    @Schema(title = "身份匹配分数", description = "取值范围为0到1，未提供匹配分数时为空")
    private Float score;

    /**
     * 身份提供方扩展信息。
     */
    @Nullable
    @Schema(title = "其他信息", description = "用于保存身份提供方的非标准扩展信息")
    private Map<String, Object> others;

    public static Identity of(String profile, String id) {
        Identity identity = new Identity();
        identity.setProfile(profile);
        identity.setId(id);
        return identity;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(profile, out);
        SerializeUtils.writeNullableUTF(id, out);
        SerializeUtils.writeNullableUTF(name, out);
        out.writeBoolean(score != null);
        if (score != null) {
            out.writeFloat(score);
        }
        SerializeUtils.writeKeyValue(others, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        profile = SerializeUtils.readNullableUTF(in);
        id = SerializeUtils.readNullableUTF(in);
        name = SerializeUtils.readNullableUTF(in);
        score = in.readBoolean() ? in.readFloat() : null;
        others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
    }
}
