package org.jetlinks.sdk.server.ai.cv.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.jetlinks.core.utils.SerializeUtils;

import javax.annotation.Nullable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class KeyPointObject implements Externalizable {

    public static final String ANNOTATIONS_KEY = "keyPoints";

    @Schema(title = "关键点类别标识")
    @Nullable
    private String keyPointsLabel;

    @Schema(title = "关键点类别置信度")
    private float keyPointsScore;

    @Schema(title = "关键点")
    private List<KeyPoint> keyPoints;


    public void add(KeyPoint... keyPoint) {
        if (keyPoints == null) {
            keyPoints = new ArrayList<>();
        }
        keyPoints.addAll(List.of(keyPoint));
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(keyPointsLabel, out);
        out.writeFloat(keyPointsScore);
        if (CollectionUtils.isEmpty(keyPoints)) {
            out.writeInt(0);
        } else {
            out.writeInt(keyPoints.size());
            for (KeyPoint keyPoint : keyPoints) {
                keyPoint.writeExternal(out);
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        keyPointsLabel = SerializeUtils.readNullableUTF(in);
        keyPointsScore = in.readFloat();
        int size = in.readInt();
        if (size > 0) {
            keyPoints = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                KeyPoint keyPoint = new KeyPoint();
                keyPoint.readExternal(in);
                keyPoints.add(keyPoint);
            }
        } else {
            keyPoints = new ArrayList<>(0);
        }
    }
}
