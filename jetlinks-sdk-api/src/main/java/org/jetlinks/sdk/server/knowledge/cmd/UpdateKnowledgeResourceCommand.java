package org.jetlinks.sdk.server.knowledge.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.sdk.server.knowledge.KnowledgeResourceInfo;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Mono;

import java.util.List;

@Schema(title = "更新知识库资源信息", description = "仅更新资源信息，不更新资源内容")
public class UpdateKnowledgeResourceCommand extends AbstractConvertCommand<Mono<Void>,
    UpdateKnowledgeResourceCommand> {

    @Schema(title = "资源")
    public List<KnowledgeResourceInfo> getResources() {
        return ConverterUtils.convertToList(readable().get("resources"), o -> {
            if (o instanceof KnowledgeResourceInfo) {
                return (KnowledgeResourceInfo) o;
            }
            return FastBeanCopier.copy(o, new KnowledgeResourceInfo());
        });
    }

    public UpdateKnowledgeResourceCommand setResources(List<KnowledgeResourceInfo> resources) {
        return with("resources", resources);
    }

}
