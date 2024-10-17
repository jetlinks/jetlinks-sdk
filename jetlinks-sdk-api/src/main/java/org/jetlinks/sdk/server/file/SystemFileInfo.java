package org.jetlinks.sdk.server.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

/**
 * 系统文件信息.
 *
 * @author zhangji 2024/10/15
 * @since 2.3
 */
@Getter
@Setter
public class SystemFileInfo {

    @Schema(description = "文件绝对路径")
    private String path;

    @Schema(description = "文件名")
    private String name;

    @Schema(description = "文件类型")
    private MediaType mediaType;

    @Schema(description = "是否目录")
    private boolean directory;

    @Schema(description = "文件大小（单位：字节）")
    private long size;

    @Schema(description = "创建时间")
    private long createTime;

    @Schema(description = "修改时间")
    private long modifyTime;

}
