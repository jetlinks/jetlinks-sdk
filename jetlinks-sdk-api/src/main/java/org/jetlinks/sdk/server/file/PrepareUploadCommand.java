package org.jetlinks.sdk.server.file;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import reactor.core.publisher.Mono;

/**
 * 预存储文件信息命令，用于在文件内容保存前就存储相关信息.适用于异步上传文件,优先获取文件id进行处理的场景
 *
 * @see org.jetlinks.sdk.server.SdkServices#fileService
 * @since 1.0.1
 */
@Schema(title = "预存储文件")
public class PrepareUploadCommand extends AbstractCommand<Mono<FileInfo>, PrepareUploadCommand> {

    @Schema(title = "文件名称")
    public String getName() {
        return (String) readable().get("name");
    }

    @Schema(title = "文件长度")
    public long getLength() {
        return (long) readable().get("length");
    }

    @Schema(title = "md5校验码")
    public String getMd5() {
        return getOrNull("md5", String.class);
    }

    @Schema(title = "sha256校验码")
    public String getSha256() {
        return getOrNull("sha256", String.class);
    }

    @Schema(title = "上传时间")
    public long getCreateTime() {
        return (long) readable().get("createTime");
    }

    /**
     * 设置文件信息
     *
     * @param request 文件内容长度
     * @return this
     */
    public PrepareUploadCommand withRequest(PrepareUploadRequest request) {
        return with(request.toMap());
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(PrepareUploadCommand.class);
    }

}