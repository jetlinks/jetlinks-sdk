package org.jetlinks.sdk.server.media;

import io.netty.buffer.ByteBuf;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;

@Schema(title = "获取设备视频流截图")
public class GetDeviceSnapCommand extends AbstractCommand<Flux<ByteBuf>, GetDeviceSnapCommand> {

    @Schema(description = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public GetDeviceSnapCommand setDeviceId(String deviceId) {
        return with("deviceId", deviceId);
    }

    @Schema(description = "通道ID")
    public String getChannelId() {
        return getOrNull("channelId", String.class);
    }

    public GetDeviceSnapCommand setChannelId(String channelId) {
        return with("channelId", channelId);
    }

    @Schema(title = "缓存时间（秒）", description = "优先返回该缓存时间内的截图")
    public Integer getExpire() {
        return getOrNull("expire", Integer.class);
    }

    public GetDeviceSnapCommand setExpire(int expire) {
        return with("expire", expire);
    }

    @Schema(description = "超时时间")
    public Integer getTimeout() {
        return getOrNull("timeout", Integer.class);
    }

    public GetDeviceSnapCommand setTimeout(int timeout) {
        return with("timeout", timeout);
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(ResolvableType.forType(GetDeviceSnapCommand.class));
    }
}
