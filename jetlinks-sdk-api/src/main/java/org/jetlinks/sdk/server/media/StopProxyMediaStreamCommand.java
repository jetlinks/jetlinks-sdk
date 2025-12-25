package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.StringType;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * 停止代理视频流
 *
 * @author zhouhao
 * @since 2.2
 */
public class StopProxyMediaStreamCommand extends AbstractCommand<Mono<Void>, StopProxyMediaStreamCommand> {

    public String getStreamId() {
        return (String) readable().get("streamId");
    }


    public StopProxyMediaStreamCommand withStreamId(String streamId) {
        with("streamId", streamId);
        return this;
    }

    @Schema(title = "所属应用",description = "代理后的流的所属应用")
    public String getApp(){
        return getOrNull("app",String.class);
    }

    public StopProxyMediaStreamCommand withApp(String app) {
        with("app", app);
        return this;
    }


    public static FunctionMetadata metadata() {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(StopProxyMediaStreamCommand.class));
        metadata.setName("停止代理视频流");
        metadata.setInputs(
                Collections.singletonList(
                        SimplePropertyMetadata.of("streamId", "流ID", StringType.GLOBAL)
                )
        );

        return metadata;
    }

}
