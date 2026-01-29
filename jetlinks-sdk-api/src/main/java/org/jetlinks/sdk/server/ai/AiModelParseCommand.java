package org.jetlinks.sdk.server.ai;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.ai.model.AiModelParseInfo;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Schema(title = "解析Ai模型")
public class AiModelParseCommand extends AbstractConvertCommand<Mono<AiModelParseInfo>, AiModelParseCommand> {

    private final String FILE_URL = "fileUrl";
    private final String OTHERS = "others";

    @Schema(title = "模型文件地址")
    public String getFileUrl() {
        Object val = this.readable().get(FILE_URL);
        return val == null ? "" : val.toString();
    }

    public void setFileUrl(String fileUrl) {
        this.writable().put(FILE_URL, fileUrl);
    }

    @Schema(title = "模型文件其他配置")
    public Map<String, Object> getOthers() {
        Object val = this.readable().get(OTHERS);
        return val == null ? new HashMap<>() : FastBeanCopier.copy(val, new HashMap<>());
    }

    public void setOthers(Map<String, Object> others) {
        this.writable().put(OTHERS, others);
    }


    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(AiModelParseCommand.class);
    }
}
