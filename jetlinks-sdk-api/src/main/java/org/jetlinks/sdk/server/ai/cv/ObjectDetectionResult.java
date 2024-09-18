package org.jetlinks.sdk.server.ai.cv;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Schema(title = "目标检测结果")
public class ObjectDetectionResult extends AiTaskCommandResult<ObjectDetectionResult> {

    @Schema(title = "图像数据")
    private ByteBuf image;

    @Schema(title = "检测到的对象")
    private List<DetectedObject> objects;

    @Schema(title = "其他信息")
    private Map<String, Object> others;


    @Getter
    @Setter
    public static class DetectedObject implements Externalizable {
        /**
         * 识别出的对象ID,为空说明没有识别出对象
         *
         * @see ComputerVisionObject#getId()
         */
        @Schema(title = "对象ID")
        private String objectId;

        @Schema(title = "标签")
        private String label;

        @Schema(title = "置信度")
        private float score;

        @Schema(title = "边框")
        private float[] box;

        @Schema(title = "标注信息")
        private Map<String, Object> annotations;

        @Schema(title = "其他信息")
        private Map<String, Object> others;

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            SerializeUtils.writeNullableUTF(objectId, out);
            SerializeUtils.writeNullableUTF(label, out);
            out.writeFloat(score);
            out.writeInt(box == null ? 0 : box.length);
            if (box != null) {
                for (float v : box) {
                    out.writeFloat(v);
                }
            }
            SerializeUtils.writeKeyValue(annotations, out);
            SerializeUtils.writeKeyValue(others, out);

        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            objectId = SerializeUtils.readNullableUTF(in);
            label = SerializeUtils.readNullableUTF(in);
            score = in.readFloat();
            int size = in.readInt();
            box = new float[size];
            for (int i = 0; i < size; i++) {
                box[i] = in.readFloat();
            }
            annotations = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
            others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        SerializeUtils.writeObject(image, out);
        if (CollectionUtils.isEmpty(objects)) {
            out.writeInt(0);
        } else {
            out.writeInt(objects.size());
            for (DetectedObject object : objects) {
                object.writeExternal(out);
            }
        }

        SerializeUtils.writeKeyValue(others, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        image = (ByteBuf) SerializeUtils.readObject(in);
        int size = in.readInt();
        if (size > 0) {
            objects = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                DetectedObject object = new DetectedObject();
                object.readExternal(in);
                objects.add(object);
            }
        } else {
            objects = new ArrayList<>(0);
        }
        others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
    }
}
