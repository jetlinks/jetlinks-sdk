package org.jetlinks.sdk.server.ai.cv;

import io.netty.buffer.ByteBuf;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 计算机视觉对象，如: 人脸，车辆等.
 *
 * @author zhouhao
 * @since 1.0.1
 */
@Getter
@Setter
public class ComputerVisionObject {

    @Schema(title = "对象ID")
    private String id;

    @Schema(title = "对象名称")
    private String name;

    @Schema(title = "对象类型")
    private String type;

    @Schema(title = "图像信息")
    private List<Image> images;

    @Schema(title = "元数据信息")
    private Map<String, Object> metadata;

    @Schema(title = "标注信息")
    private Map<String, Object> annotations;

    @Getter
    @Setter
    public static class Image {

        @Schema(title = "图像ID")
        private String id;

        @Schema(title = "尺寸")
        private int[] size;

        @Schema(title = "图像URL")
        private String url;

        @Schema(title = "图像数据")
        private ByteBuf data;

        @Schema(title = "其他信息")
        private Map<String, Object> others;

    }

}
