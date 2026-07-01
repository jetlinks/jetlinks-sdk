package org.jetlinks.sdk.server.ai.cv.data;

import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
    public static final String TYPE_LINE = "line";
    public static final String TYPE_AREA = "area";

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

        @Schema(title = "ROI坐标点")
        private List<Point> points;

        @Schema(title = "ROI类型", description = "line为线段,area为区域")
        private String type;

        // 线段方向由起点到终点顺时针旋转90度后的方向为入口。
        @Nullable
        @Schema(title = "是否进入", description = "true为进入")
        private Boolean enter;

        @Schema(title = "其他信息")
        private Map<String, Object> others;

        public static Roi of(String points, String type, Boolean enter, Map<String, Object> others) {
            return new Roi(Point.from(points), type, enter, others);
        }

        public static Roi line(List<Point> points, boolean enter, Map<String, Object> others) {
            return new Roi(points, TYPE_LINE, enter, others);
        }

        public static Roi area(List<Point> points, Map<String, Object> others) {
            return new Roi(points, TYPE_AREA, null, others);
        }

        public boolean isLine() {
            return TYPE_LINE.equals(type);
        }

        public boolean isArea() {
            return TYPE_AREA.equals(type);
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            if (CollectionUtils.isEmpty(points)) {
                out.writeInt(0);
            } else {
                out.writeInt(points.size());
                for (Point point : points) {
                    point.writeExternal(out);
                }
            }
            SerializeUtils.writeNullableUTF(type, out);
            out.writeBoolean(enter != null);
            if (enter != null) {
                out.writeBoolean(enter);
            }
            SerializeUtils.writeKeyValue(others, out);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            int size = in.readInt();
            if (size > 0) {
                points = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    Point point = new Point();
                    point.readExternal(in);
                    points.add(point);
                }
            } else {
                points = new ArrayList<>(0);
            }
            type = SerializeUtils.readNullableUTF(in);
            if (in.readBoolean()) {
                enter = in.readBoolean();
            }
            others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
        }
    }

    /**
     * ROI坐标点。
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Point implements Externalizable {

        @Schema(title = "x轴坐标")
        private float coordinateX;

        @Schema(title = "y轴坐标")
        private float coordinateY;

        public static Point of(float coordinateX, float coordinateY) {
            return new Point(coordinateX, coordinateY);
        }

        public static List<Point> from(String points) {
            if (StringUtils.isBlank(points)) {
                return new ArrayList<>(0);
            }
            String[] pointValues = StringUtils.split(points, ';');
            List<Point> result = new ArrayList<>(pointValues.length);
            for (String pointValue : pointValues) {
                String[] coordinates = StringUtils.split(pointValue, ',');
                if (coordinates.length != 2) {
                    throw new IllegalArgumentException("Illegal ROI point format: " + pointValue);
                }
                result.add(Point.of(
                    Float.parseFloat(StringUtils.trim(coordinates[0])),
                    Float.parseFloat(StringUtils.trim(coordinates[1]))
                ));
            }
            return result;
        }

        public static String to(List<Point> points) {
            if (CollectionUtils.isEmpty(points)) {
                return "";
            }
            StringBuilder builder = new StringBuilder();
            for (Point point : points) {
                if (!builder.isEmpty()) {
                    builder.append(';');
                }
                builder
                    .append(point.getCoordinateX())
                    .append(',')
                    .append(point.getCoordinateY());
            }
            return builder.toString();
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeFloat(coordinateX);
            out.writeFloat(coordinateY);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            coordinateX = in.readFloat();
            coordinateY = in.readFloat();
        }
    }
}
