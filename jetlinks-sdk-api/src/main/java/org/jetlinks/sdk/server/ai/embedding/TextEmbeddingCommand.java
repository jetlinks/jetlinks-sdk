package org.jetlinks.sdk.server.ai.embedding;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.ai.AiCommand;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;

import java.util.List;

@Schema(title = "文本向量化")
public class TextEmbeddingCommand extends AbstractCommand<Flux<TextEmbedding>, TextEmbeddingCommand>
    implements AiCommand<TextEmbedding> {


    @Schema(title = "输入文本")
    public List<String> getInputs() {
        return ConverterUtils.convertToList(
            readable().get("inputs"),
            String::valueOf);
    }


}
