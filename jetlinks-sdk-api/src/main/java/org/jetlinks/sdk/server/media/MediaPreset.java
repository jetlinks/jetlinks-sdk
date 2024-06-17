package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 预置位.
 *
 * @author zhangji 2024/6/17
 */
@Getter
@Setter
public class MediaPreset {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "其他拓展信息")
    private Map<String, Object> others;

}
