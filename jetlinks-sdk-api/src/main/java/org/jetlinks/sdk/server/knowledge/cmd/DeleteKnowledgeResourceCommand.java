package org.jetlinks.sdk.server.knowledge.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Schema(title = "删除知识库资源")
public class DeleteKnowledgeResourceCommand extends AbstractConvertCommand<Mono<Void>, DeleteKnowledgeResourceCommand> {

    @Schema(title = "知识库id")
    public List<String> getIds() {
        return ConverterUtils.convertToList(readable().get("ids"), String::valueOf);
    }

    public DeleteKnowledgeResourceCommand setIds(Collection<String> text) {
        return with("ids", new HashSet<>(text));
    }

    public DeleteKnowledgeResourceCommand setId(String text) {
        return with("ids", Collections.singletonList(text));
    }

}
