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
 * 目标检测对象关联的特征识别结果。
 */
@Getter
@Setter
@Schema(title = "目标特征")
public class Feature implements Externalizable {

    public static final String ANNOTATIONS_KEY = "features";

    /**
     * 模型输出的特征值。
     */
    @Schema(title = "特征值", description = "模型输出的单个特征值，如red、glasses")
    private String value;

    /**
     * 特征值置信度。
     */
    @Nullable
    @Schema(title = "置信度", description = "取值范围为0到1，未提供置信度时为空")
    private Float confidence;

    /**
     * 特征识别提供方扩展信息。
     */
    @Nullable
    @Schema(title = "其他信息", description = "用于保存特征识别提供方的非标准扩展信息")
    private Map<String, Object> others;

    public static Feature of(String value) {
        Feature feature = new Feature();
        feature.setValue(value);
        return feature;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(value, out);
        out.writeBoolean(confidence != null);
        if (confidence != null) {
            out.writeFloat(confidence);
        }
        SerializeUtils.writeKeyValue(others, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        value = SerializeUtils.readNullableUTF(in);
        confidence = in.readBoolean() ? in.readFloat() : null;
        others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
    }
}
