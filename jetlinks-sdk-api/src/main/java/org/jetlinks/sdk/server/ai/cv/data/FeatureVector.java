package org.jetlinks.sdk.server.ai.cv.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import javax.annotation.Nullable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * 目标检测对象关联的特征向量。
 * <p>
 * 向量固定使用 float32，维度、距离算法等信息由 {@link #profile} 对应的平台配置定义。
 */
@Getter
@Setter
@Schema(title = "特征向量")
public class FeatureVector implements Externalizable {

    public static final String ANNOTATIONS_KEY = "vector";

    /**
     * 向量模型配置标识
     */
    @Schema(title = "向量模型配置标识")
    private String profile;

    /**
     * 向量质量分。
     */
    @Nullable
    @Schema(title = "向量质量分", description = "取值范围为0到1，值越大表示向量质量越高")
    private Float qualityScore;

    /**
     * float32 特征向量。
     */
    @Schema(title = "向量值", description = "float32特征向量")
    private float[] values;

    public static FeatureVector of(String profile, float... values) {
        FeatureVector vector = new FeatureVector();
        vector.setProfile(profile);
        vector.setValues(values);
        return vector;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(profile, out);
        out.writeBoolean(qualityScore != null);
        if (qualityScore != null) {
            out.writeFloat(qualityScore);
        }
        out.writeInt(values == null ? 0 : values.length);
        if (values != null) {
            for (float value : values) {
                out.writeFloat(value);
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        profile = SerializeUtils.readNullableUTF(in);
        qualityScore = in.readBoolean() ? in.readFloat() : null;
        int size = in.readInt();
        if (size < 0) {
            throw new IOException("invalid feature vector size:" + size);
        }
        values = new float[size];
        for (int i = 0; i < size; i++) {
            values[i] = in.readFloat();
        }
    }

    @Override
    public String toString() {
        return "FeatureVector{" +
            "profile='" + profile + '\'' +
            ", dimension=" + (values == null ? 0 : values.length) +
            '}';
    }
}
