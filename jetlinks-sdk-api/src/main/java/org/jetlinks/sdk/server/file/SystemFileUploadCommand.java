package org.jetlinks.sdk.server.file;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

/**
 * 上传文件到系统中.
 *
 * @author zhangji 2024/10/15
 * @since 2.3
 */
@Schema(description = "上传文件到系统中")
public class SystemFileUploadCommand extends AbstractCommand<Mono<Void>, SystemFileUploadCommand> {

    private static final long serialVersionUID = 8218475055642767541L;

    public String getFileName() {
        return (String) readable().get("fileName");
    }

    public SystemFileUploadCommand withFileName(String name) {
        return with("fileName", name);
    }

    public ByteBuf getContent() {

        Object content = readable().get("content");
        if (content instanceof ByteBuf) {
            return (ByteBuf) content;
        }
        if (content instanceof byte[]) {
            return Unpooled.wrappedBuffer(((byte[]) content));
        }
        if (content instanceof String) {
            return Unpooled.wrappedBuffer(Base64Utils.decodeFromString(((String) content)));
        }

        throw new UnsupportedOperationException("unsupported file content " + content);
    }

    public SystemFileUploadCommand withContent(ByteBuf content) {
        return with("content", content);
    }

    public static FunctionMetadata metadata(){
        return CommandMetadataResolver.resolve(SystemFileUploadCommand.class);
    }
}
