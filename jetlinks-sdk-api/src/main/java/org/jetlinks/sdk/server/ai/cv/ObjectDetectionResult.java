package org.jetlinks.sdk.server.ai.cv;

import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.GenericHeaderSupport;
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
    public static final String OBJECTS = "objects";

    @Schema(title = "图像数据")
    private List<ImageData> images;

    @Schema(title = "检测到的对象")
    private List<DetectedObject> objects;

    @Schema(title = "其他信息")
    private Map<String, Object> others;

    @Override
    public List<? extends FileData> files() {
        return images;
    }

    @Override
    public Map<String, Object> schemaMap() {
        return Collections.singletonMap(OBJECTS, objects);
    }

    @Override
    public Map<String, Object> toLightWeighMap() {
        Map<String, Object> map = FastBeanCopier.copy(this, new HashMap<>(), IMAGES);
        map.put(IMAGES, imagesToSimpleMap());
        return map;
    }

    @Override
    public List<Map<String, Object>> flat() {
        List<Map<String, Object>> maps;
        List<Map<String, Object>> _images = imagesToSimpleMap();
        if (CollectionUtils.isNotEmpty(objects)) {
            maps = new ArrayList<>(objects.size());
            for (DetectedObject object : objects) {
                FlatData from = FlatData.from(this, object);
                Map<String, Object> data = FastBeanCopier.copy(from, new HashMap<>());
                data.put(IMAGES, _images);
                maps.add(data);
            }
        } else {
            Map<String, Object> copy = FastBeanCopier.copy(this, new HashMap<>(), IMAGES, "objects");
            copy.put(IMAGES, _images);
            maps = Collections.singletonList(copy);
        }
        return maps;
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

        SerializeUtils.writeKeyValue(others, out);
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
        others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
    }


    @Getter
    @Setter
    public static class FlatData extends GenericHeaderSupport<FlatData> {
        @Schema(title = "数据id")
        private String outputId;

        @Schema(title = "是否成功响应")
        private boolean success;

        @Schema(title = "错误信息")
        private String errorMessage;

        @Schema(title = "错误码")
        private String errorCode;

        @Schema(title = "时间戳")
        private long timestamp;

        @Schema(description = "目标检测源id,例如视频源id")
        private String sourceId;

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

        @Schema(title = "对象其他信息")
        private Map<String, Object> objectOthers;

        @Schema(title = "其他信息")
        private Map<String, Object> others;


        public static FlatData from(ObjectDetectionResult result, DetectedObject object) {
            FlatData copy = FastBeanCopier.copy(result, new FlatData(), "images", "objects");
            FlatData data = FastBeanCopier.copy(object, copy, "others");
            data.setObjectOthers(object.getOthers());
            return data;
        }

    }

}
