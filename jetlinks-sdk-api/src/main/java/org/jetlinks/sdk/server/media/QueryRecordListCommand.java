package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.commons.cmd.QueryListCommand;
import org.springframework.core.ResolvableType;

/**
 * 查询录像.
 *
 * @author zhangji 2024/6/16
 */
public class QueryRecordListCommand extends QueryListCommand<MediaRecord> {

    @Schema(description = "设备ID")
    public String getDeviceId() {
        return getOrNull("deviceId", String.class);
    }

    public QueryRecordListCommand setDeviceId(String deviceId) {
        writable().put("deviceId", deviceId);
        return this;
    }

    @Schema(description = "通道ID")
    public String getChannelId() {
        return getOrNull("channelId", String.class);
    }

    public QueryRecordListCommand setChannelId(String channelId) {
        writable().put("channelId", channelId);
        return this;
    }

    @Schema(description = "录像开始时间")
    public Long getStartTime() {
        return getOrNull("startTime", Long.class);
    }

    public QueryRecordListCommand setStartTime(Long startTime) {
        writable().put("startTime", startTime);
        return this;
    }

    //录制结束时间
    @Schema(description = "录像结束时间")
    public Long getEndTime() {
        return getOrNull("endTime", Long.class);
    }

    public QueryRecordListCommand setEndTime(Long endTime) {
        writable().put("endTime", endTime);
        return this;
    }

    @Schema(description = "录像类型")
    public String getType() {
        return getOrNull("type", String.class);
    }

    public QueryRecordListCommand setType(String type) {
        writable().put("type", type);
        return this;
    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(ResolvableType.forType(QueryRecordListCommand.class));
    }

}
