package org.jetlinks.sdk.server.media;

import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.utils.CastUtils;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;

/**
 * 代理视频流命令
 *
 * @author zhouhao
 * @since 2.1
 */
public class ProxyMediaStreamCommand extends AbstractCommand<Mono<MediaInfo>, ProxyMediaStreamCommand> {

    public String getStreamId() {
        return (String) readable().get("streamId");
    }

    public String getSource() {
        return (String) readable().get("source");
    }

    public String getTarget() {
        return (String) readable().get("target");
    }

    public boolean isLocalPlayer() {
        return CastUtils.castBoolean(readable().get("localPlayer"));
    }

    public ProxyMediaStreamCommand withStreamId(String streamId) {
        with("streamId", streamId);
        return this;
    }

    public ProxyMediaStreamCommand withSource(URI sourceUri) {
        with("source", sourceUri == null ? null : sourceUri.toString());
        return this;
    }

    public ProxyMediaStreamCommand withTarget(URI targetUri) {
        with("target", targetUri == null ? null : targetUri.toString());
        return this;
    }

    public ProxyMediaStreamCommand withSource(String sourceUri) {
        with("source", sourceUri);
        return this;
    }

    public ProxyMediaStreamCommand withTarget(String targetUri) {
        with("target", targetUri);
        return this;
    }

    public ProxyMediaStreamCommand withLocalPlayer(boolean localPlayer) {
        with("localPlayer", localPlayer);
        return this;
    }


    public static FunctionMetadata metadata(){
        SimpleFunctionMetadata metadata=new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(ProxyMediaStreamCommand.class));
        metadata.setName("代理视频流");
        metadata.setInputs(
            Arrays.asList(
                SimplePropertyMetadata.of("streamId", "流ID", StringType.GLOBAL),
                SimplePropertyMetadata.of("source", "源地址", StringType.GLOBAL),
                SimplePropertyMetadata.of("target", "目标地址", StringType.GLOBAL),
                SimplePropertyMetadata.of("localPlayer", "本地播放", StringType.GLOBAL)
            )
        );
        metadata.setOutput(new ObjectType()
                               .addProperty("streamId", "流ID", StringType.GLOBAL)
                               .addProperty("rtsp", "rtsp地址", StringType.GLOBAL)
                               .addProperty("mp4", "mp4地址", StringType.GLOBAL)
                               .addProperty("flv", "flv地址", StringType.GLOBAL))
          ;
        return metadata;
    }

}
