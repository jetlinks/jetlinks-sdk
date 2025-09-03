package org.jetlinks.sdk.server.knowledge.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.sdk.server.knowledge.KnowledgeResourceInfo;
import org.jetlinks.sdk.server.utils.CastUtils;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Mono;

import java.util.List;

@Schema(title = "保存知识库资源")
public class SaveKnowledgeResourceCommand extends AbstractConvertCommand<Mono<Void>, SaveKnowledgeResourceCommand> {

    @Schema(title = "是否解析资源")
    public Boolean getParse() {
        return CastUtils.castBoolean(readable().get("parse"));
    }

    public SaveKnowledgeResourceCommand setParse(boolean lon) {
        return with("parse", lon);
    }

    @Schema(title = "资源")
    public List<KnowledgeResourceInfo> getResources() {
        return ConverterUtils.convertToList(readable().get("resources"), o -> {
            if (o instanceof KnowledgeResourceInfo) {
                return (KnowledgeResourceInfo) o;
            }
            return FastBeanCopier.copy(o, new KnowledgeResourceInfo());
        });
    }

    public SaveKnowledgeResourceCommand setResources(List<KnowledgeResourceInfo> resources) {
        return with("resources", resources);
    }

}
