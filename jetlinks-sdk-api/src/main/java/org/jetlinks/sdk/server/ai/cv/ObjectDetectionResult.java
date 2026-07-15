package org.jetlinks.sdk.server.ai.cv;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.utils.SerializeUtils;
import org.jetlinks.sdk.server.file.FileData;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.*;

@Setter
@Getter
@Schema(title = "目标检测结果")
public class ObjectDetectionResult extends AiCommandResult<ObjectDetectionResult> {

    public static final String IMAGES = "images";
    public static final String SCHEMA_KEY = "objects";

    @Schema(title = "图像数据")
    private List<ImageData> images;

    @Schema(title = "检测到的对象")
    private List<DetectedObject> objects;

    @Override
    public List<? extends FileData> files() {
        return images;
    }

    @Override
    public Map<String, Object> schemaMap() {
        return Collections.singletonMap(SCHEMA_KEY, objects);
    }

    @Override
    public JSONObject toJson() {
        return toJson0();
    }

    @Override
    public Map<String, Object> toLightWeighMap() {
        return toJson0();
    }

    private JSONObject toJson0() {
        JSONObject map = FastBeanCopier.copy(this, new JSONObject(), IMAGES);
        map.put(IMAGES, imagesToSimpleMap());
        return map;
    }

    private List<Map<String, Object>> imagesToSimpleMap() {
        if (CollectionUtils.isNotEmpty(images)) {
            //移除图片的原始数据
            List<Map<String, Object>> _images = new ArrayList<>(images.size());
            for (ImageData image : images) {
                _images.add(FastBeanCopier.copy(image, new HashMap<>(), "data"));
            }
            return _images;
        }
        return null;
    }


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

        public void addAnnotation(String key, Object value) {
            if (annotations == null) {
                annotations = Maps.newHashMap();
            }
            annotations.put(key, value);
        }

        public void addOther(String key, Object value) {
            if (others == null) {
                others = Maps.newHashMap();
            }
            others.put(key, value);
        }

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
        if (CollectionUtils.isEmpty(images)) {
            out.writeInt(0);
        } else {
            out.writeInt(images.size());
            for (ImageData img : images) {
                img.writeExternal(out);
            }
        }

        if (CollectionUtils.isEmpty(objects)) {
            out.writeInt(0);
        } else {
            out.writeInt(objects.size());
            for (DetectedObject object : objects) {
                object.writeExternal(out);
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        int sizeImg = in.readInt();
        if (sizeImg > 0) {
            images = new ArrayList<>(sizeImg);
            for (int i = 0; i < sizeImg; i++) {
                ImageData img = new ImageData();
                img.readExternal(in);
                images.add(img);
            }
        } else {
            images = new ArrayList<>(0);
        }

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
    }

}
