package org.jetlinks.sdk.server.ai.cv.data;

import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
import java.util.Map;

/**
 * 目标检测结果关联的 ROI 标注信息，用于声明识别数据命中的任务区域或线段。
 */
@Getter
@Setter
public class RoiObject implements Externalizable {

    public static final String ANNOTATIONS_KEY = "roi";

    @Schema(title = "ROI信息")
    private List<Roi> roi;

    public void add(Roi... roi) {
        if (this.roi == null) {
            this.roi = new ArrayList<>();
        }
        this.roi.addAll(List.of(roi));
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        if (CollectionUtils.isEmpty(roi)) {
            out.writeInt(0);
        } else {
            out.writeInt(roi.size());
            for (Roi item : roi) {
                item.writeExternal(out);
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        if (size > 0) {
            roi = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                Roi item = new Roi();
                item.readExternal(in);
                roi.add(item);
            }
        } else {
            roi = new ArrayList<>(0);
        }
    }

    /**
     * 单个 ROI 匹配结果。
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Roi implements Externalizable {

        @Schema(title = "ROI坐标", description = "线段或区域坐标,格式如:x1,y1;x2,y2...")
        private String roi;

        @Schema(title = "ROI类型", description = "line为线段,area为区域")
        private String type;

        //线段的进入：延线段指向（起始点->终点）顺时针旋转 90 度指向的为右侧，从左到右为进入线段
        @Nullable
        @Schema(title = "是否进入", description = "true为进入")
        private Boolean enter;

        @Schema(title = "其他信息")
        private Map<String, Object> others;

        public static Roi line(String roi, boolean enter, Map<String, Object> others) {
            return new Roi(roi, "line", enter, others);
        }

        public static Roi area(String roi, Map<String, Object> others) {
            return new Roi(roi, "area", null, others);
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            SerializeUtils.writeNullableUTF(roi, out);
            SerializeUtils.writeNullableUTF(type, out);
            out.writeBoolean(enter != null);
            if (enter != null) {
                out.writeBoolean(enter);
            }
            SerializeUtils.writeKeyValue(others, out);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            roi = SerializeUtils.readNullableUTF(in);
            type = SerializeUtils.readNullableUTF(in);
            if (in.readBoolean()) {
                enter = in.readBoolean();
            }
            others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
        }
    }
}
