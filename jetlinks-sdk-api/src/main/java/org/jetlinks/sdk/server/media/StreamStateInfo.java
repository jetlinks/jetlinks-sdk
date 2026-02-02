package org.jetlinks.sdk.server.media;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StreamStateInfo {

    //所在集群节点ID
    private String nodeId;

    private String serverId;

    private String streamId;

    private int players;

    public StreamStateInfo merge(StreamStateInfo info) {
        if (info == null) {
            return this;
        }
        if (info.getStreamId() != null) {
            this.streamId = info.getStreamId();
        }
        if (info.getServerId() != null) {
            this.serverId = info.getServerId();
        }
        this.players += info.getPlayers();
        return this;
    }
}
