package org.jetlinks.sdk.server.geo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.metadata.types.*;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
@ToString
public class GeoObjectInfo implements Serializable {

    private static final long serialVersionUID = -6849794470754667710L;

    //唯一标识
    @NotBlank(message = "[id]不能为空")
    @Schema(description = "id")
    private String id;

    //类型,如: device
    @NotBlank(message = "[objectType]不能为空")
    @Schema(description = "对象类型")
    private String objectType;

    //类型,如: point
    @NotBlank(message = "[shapeType]不能为空")
    @Schema(description = "位置类型")
    private String shapeType;

    //对象标识,如: deviceId
    @NotBlank(message = "[objectId]不能为空")
    @Schema(description = "对象id")
    private String objectId;

    //属性标识如: location
    @Schema(description = "属性标识")
    private String property;

    //坐标
    private GeoPoint point;

    //地形
    private GeoShape shape;

    //拓展信息
    @Schema(description = "拓展信息")
    private Map<String, Object> tags;

    //时间戳: 数据更新的时间
    @Schema(description = "更新时间")
    private long timestamp;

    public void setPoint(GeoPoint point) {
        this.shapeType = GeoShape.Type.Point.name();
        this.point = point;
        this.shape = GeoShape.fromPoint(point);
    }

    public void setShape(GeoShape shape) {
        if (shape == null) {
            this.shape = null;
            return;
        }
        this.shapeType = shape.getType().name();
        this.shape = shape;
        if (shape.getType() == GeoShape.Type.Point && this.point == null) {
            this.point = GeoPoint.of(shape.getCoordinates());
        }
    }

    public static GeoObjectInfo of(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof GeoObjectInfo) {
            return ((GeoObjectInfo) obj);
        }
        if (obj instanceof Map) {
            GeoObjectInfo geoObject = FastBeanCopier.copy(obj, GeoObjectInfo.class, "point", "shape");
            Map<String, Object> map = (Map) obj;
            GeoPoint point = GeoPoint.of((map).get("point"));
            if (point != null) {
                geoObject.setPoint(point);
            } else {
                GeoShape shape = GeoShape.of((map).get("shape"));
                if (shape != null) {
                    geoObject.setShape(shape);
                }
            }
        }
        return null;
    }
}
