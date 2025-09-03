package org.jetlinks.sdk.server.knowledge.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Schema(title = "删除知识库资源分类")
public class DeleteKnowledgeResourceClassifyIdCommand extends AbstractConvertCommand<Mono<Void>, DeleteKnowledgeResourceClassifyIdCommand> {

    @Schema(title = "知识库分类id")
    public List<String> getIds() {
        return ConverterUtils.convertToList(readable().get("ids"), String::valueOf);
    }

    public DeleteKnowledgeResourceClassifyIdCommand setIds(Collection<String> text) {
        return with("ids", new HashSet<>(text));
    }

    public DeleteKnowledgeResourceClassifyIdCommand setId(String text) {
        return with("ids", Collections.singletonList(text));
    }

}
