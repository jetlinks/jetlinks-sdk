package org.jetlinks.sdk.server.knowledge.cmd;


import io.swagger.v3.oas.annotations.media.Schema;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.sdk.server.knowledge.KnowledgeResourceSearchInfo;
import org.jetlinks.sdk.server.knowledge.ResourceBelongInfo;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;

import java.util.List;

@Schema(title = "检索知识库资源", description = "检索已解析的知识库资源")
public class SearchKnowledgeResourceCommand extends AbstractConvertCommand<Flux<KnowledgeResourceSearchInfo>, SearchKnowledgeResourceCommand> {

    @Schema(title = "检索内容")
    public String getText() {
        return (String) readable().get("text");
    }

    public SearchKnowledgeResourceCommand setText(String text) {
        return with("text", text);
    }

    @Schema(title = "指定检索文档所属")
    public List<ResourceBelongInfo> getBelong() {
        return ConverterUtils.convertToList(
            readable().get("belong"),
            o -> {
                if (o instanceof ResourceBelongInfo) {
                    return (ResourceBelongInfo) o;
                }
                return FastBeanCopier.copy(o, new ResourceBelongInfo());
            }
        );
    }

    public SearchKnowledgeResourceCommand setBelong(List<ResourceBelongInfo> belongInfos) {
        return with("belong", belongInfos);
    }


    @Schema(title = "最小关联度")
    public float getConfidence() {
        return (float) readable().getOrDefault("confidence", 0.5);
    }

    public SearchKnowledgeResourceCommand setConfidence(float confidence) {
        return with("confidence", confidence);
    }

}
