package org.jetlinks.sdk.server.media;

import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.StringType;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;

/**
 * 关闭视频流命令
 *
 * @author zhouhao
 * @since 2.2
 */
public class CloseStreamCommand extends AbstractCommand<Mono<Void>, CloseStreamCommand> {

    public String getDeviceId() {
        return (String) readable().get("deviceId");
    }

    public CloseStreamCommand withDeviceId(String deviceId) {
        with("deviceId", deviceId);
        return this;
    }

    public String getStreamId() {
        return (String) readable().get("streamId");
    }

    public CloseStreamCommand withStreamId(String streamId) {
        with("streamId", streamId);
        return this;
    }


    public static FunctionMetadata metadata() {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(CloseStreamCommand.class));
        metadata.setName("关闭视频流");
        metadata.setInputs(
            Arrays.asList(
                SimplePropertyMetadata.of("deviceId", "视频设备ID", StringType.GLOBAL),
                SimplePropertyMetadata.of("streamId", "流ID", StringType.GLOBAL)
            )
        );

        return metadata;
    }

}
