package org.jetlinks.sdk.server.ai.llm.agent;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.List;

@Schema(title = "一次性对话调用命令")
public class OnceChatCommand extends AbstractCommand<Mono<SimpleChatResponse>, OnceChatCommand> {

    @Schema(title = "内容")
    public String getContent() {
        return getOrNull("content", String.class);
    }

    public OnceChatCommand setContent(String content) {
        return with("content", content);
    }

    @Schema(title = "文件")
    public List<FileInfo> getFiles() {
        return ConverterUtils.convertToList(readable().get("files"), f -> {
            if (f instanceof FileInfo) {
                return (FileInfo) f;
            }
            return FastBeanCopier.copy(f, new FileInfo());
        });
    }

    public OnceChatCommand setFiles(List<FileInfo> url) {
        return with("files", url);
    }

    @Getter
    @Setter
    public static class FileInfo {
        private MediaType mediaType;
        private String fileUrl;
        private String fileName;
    }

}
