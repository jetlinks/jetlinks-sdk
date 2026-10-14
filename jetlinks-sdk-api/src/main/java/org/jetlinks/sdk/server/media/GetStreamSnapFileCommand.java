package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.file.FileInfo;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Mono;

@Schema(title = "获取视频流截图并保存为文件",description = "进行截图并存储,返回已经被存储的截图文件信息")
public class GetStreamSnapFileCommand extends AbstractCommand<Mono<FileInfo>, GetStreamSnapFileCommand> {

    @Schema(description = "视频流url")
    public String getUrl() {
        return getOrNull("url", String.class);
    }

    public GetStreamSnapFileCommand setUrl(String url) {
        return with("url", url);
    }

    @Schema(title = "缓存时间（秒）", description = "优先返回该缓存时间内的截图")
    public Integer getExpire() {
        return getOrNull("expire", Integer.class);
    }

    public GetStreamSnapFileCommand setExpire(int expire) {
        return with("expire", expire);
    }

    @Schema(description = "超时时间")
    public Integer getTimeout() {
        return getOrNull("timeout", Integer.class);
    }

    public GetStreamSnapFileCommand setTimeout(int timeout) {
        return with("timeout", timeout);
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(ResolvableType.forType(GetStreamSnapFileCommand.class));
    }

}
