package org.jetlinks.sdk.server.knowledge.cmd;


import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.sdk.server.commons.cmd.QueryListCommand;
import org.jetlinks.sdk.server.knowledge.KnowledgeResourceInfo;

@Schema(title = "查询知识库资源")
public class QueryKnowledgeResourceCommand extends QueryListCommand<KnowledgeResourceInfo> {

    
}
