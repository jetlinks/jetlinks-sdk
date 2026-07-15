package org.jetlinks.sdk.server.ai.cv.data;

import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

@Getter
@Setter
public class KeyPoint implements Externalizable {

    @Schema(title = "关键点索引",description = "从0开始")
    private int index;

    @Schema(title = "关键点名称")
    private String name;

    @Schema(title = "x轴坐标")
    private float x;

    @Schema(title = "y轴坐标")
    private float y;

    @Schema(title = "z轴坐标")
    private float z;

    @Schema(title = "置信度")
    private float score;

    @Schema(title = "可见性")
    private Visibility visibility;

    @Schema(title = "其他信息")
    private Map<String, Object> others;

    public static KeyPoint of(int index, float coordinateX, float coordinateY) {
        return of(index, null, coordinateX, coordinateY, 0, 0, null);
    }

    public static KeyPoint of(int index, float coordinateX, float coordinateY, float score) {
        return of(index, null, coordinateX, coordinateY, 0, score, null);
    }

    public static KeyPoint of(int index, String name, float coordinateX, float coordinateY, float score) {
        return of(index, name, coordinateX, coordinateY, 0, score, null);
    }

    public static KeyPoint of(int index,
                              String name,
                              float coordinateX,
                              float coordinateY,
                              float coordinateZ,
                              float score,
                              Visibility visibility) {
        KeyPoint point = new KeyPoint();
        point.setIndex(index);
        point.setName(name);
        point.setX(coordinateX);
        point.setY(coordinateY);
        point.setZ(coordinateZ);
        point.setScore(score);
        point.setVisibility(visibility);
        return point;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(index);
        SerializeUtils.writeNullableUTF(name, out);
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
        out.writeFloat(score);
        SerializeUtils.writeNullableUTF(visibility == null ? null : visibility.name(), out);
        SerializeUtils.writeKeyValue(others, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        index = in.readInt();
        name = SerializeUtils.readNullableUTF(in);
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
        score = in.readFloat();
        String visibility = SerializeUtils.readNullableUTF(in);
        if (visibility != null) {
            this.visibility = Visibility.valueOf(visibility);
        }
        others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
    }


    @Getter
    @AllArgsConstructor
    public enum Visibility {
        notLabeled("未标注"),
        Invisible("标注但不可见"),
        labeled("标注可见"),
        ;
        private final String text;
    }
}
