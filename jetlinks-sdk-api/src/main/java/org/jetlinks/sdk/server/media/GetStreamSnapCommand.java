package org.jetlinks.sdk.server.media;

import io.netty.buffer.ByteBuf;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Schema(title = "获取视频流截图")
public class GetStreamSnapCommand extends AbstractCommand<Flux<ByteBuf>, GetStreamSnapCommand> {

    @Schema(description = "视频流url")
    public String getUrl() {
        return getOrNull("url", String.class);
    }

    public GetStreamSnapCommand setUrl(String url) {
        return with("url", url);
    }

    @Schema(title = "缓存时间（秒）", description = "优先返回该缓存时间内的截图")
    public Integer getExpire() {
        return getOrNull("expire", Integer.class);
    }

    public GetStreamSnapCommand setExpire(int expire) {
        return with("expire", expire);
    }

    @Schema(description = "超时时间")
    public Integer getTimeout() {
        return getOrNull("timeout", Integer.class);
    }

    public GetStreamSnapCommand setTimeout(int timeout) {
        return with("timeout", timeout);
    }

}
