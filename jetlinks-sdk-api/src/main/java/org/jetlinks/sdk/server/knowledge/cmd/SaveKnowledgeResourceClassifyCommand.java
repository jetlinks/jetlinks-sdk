package org.jetlinks.sdk.server.knowledge.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.sdk.server.knowledge.KnowledgeResourceClassifyInfo;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Mono;

import java.util.List;

@Schema(title = "保存知识库资源分类")
public class SaveKnowledgeResourceClassifyCommand extends AbstractConvertCommand<Mono<Void>, SaveKnowledgeResourceClassifyCommand> {

    @Schema(title = "分类")
    public List<KnowledgeResourceClassifyInfo> getClassify() {
        return ConverterUtils.convertToList(readable().get("classify"), o -> {
            if (o instanceof KnowledgeResourceClassifyInfo) {
                return (KnowledgeResourceClassifyInfo) o;
            }
            return FastBeanCopier.copy(o, new KnowledgeResourceClassifyInfo());
        });
    }

    public SaveKnowledgeResourceClassifyCommand setClassify(List<KnowledgeResourceClassifyInfo> resources) {
        return with("classify", resources);
    }

}
