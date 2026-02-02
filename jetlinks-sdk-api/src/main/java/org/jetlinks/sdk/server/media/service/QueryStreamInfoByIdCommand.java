package org.jetlinks.sdk.server.media.service;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.media.StreamStateInfo;
import reactor.core.publisher.Mono;

public class QueryStreamInfoByIdCommand extends AbstractCommand<Mono<StreamStateInfo>, QueryStreamInfoByIdCommand> {

    @Schema(title = "流id")
    public String getStreamId() {
        return getOrNull("streamId", String.class);
    }

    public QueryStreamInfoByIdCommand setStramId(String streamId) {
        writable().put("streamId", streamId);
        return this;
    }

    @Schema(title = "仅当前节点")
    public Boolean getCurrentNode() {
        return getOrNull("currentNode", Boolean.class);
    }

    public QueryStreamInfoByIdCommand setCurrentNode(boolean currentNode) {
        writable().put("currentNode", currentNode);
        return this;
    }

}

