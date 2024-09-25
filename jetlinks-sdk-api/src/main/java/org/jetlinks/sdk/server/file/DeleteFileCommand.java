package org.jetlinks.sdk.server.file;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * 删除文件命令
 * <pre>
 * 根据删除方式,填充对应参数.
 * type=[ids]时,参数ids不得为空
 * type=[beforeTime]时,参数beforeTime不得为空
 * </pre>
 */
@Schema(title = "删除文件")
public class DeleteFileCommand extends AbstractCommand<Mono<Void>, DeleteFileCommand> {

    @Schema(title = "删除方式")
    public Type getType() {
        String type = (String) readable().get("type");
        return Type.valueOf(type);
    }

    /**
     * type=[ids]时不为空
     */
    @Schema(title = "待删除文件id")
    public Set<String> getIds() {
        return getOrNull("ids", ResolvableType
            .forClassWithGenerics(Set.class, String.class)
            .getType());
    }

    /**
     * type=[beforeTime]时不为空
     */
    @Schema(title = "待删除文件小于的创建时间")
    public long getBeforeTime() {
        Long beforeTime = getOrNull("beforeTime", Long.class);
        return beforeTime == null ? 0 : beforeTime;
    }


    @Schema(title = "是否仅删除临时文件")
    public boolean getOnlyDelTempFile() {
        return (boolean) readable().get("onlyDelTempFile");
    }

    public DeleteFileCommand setType(Type type) {
        return with("type", type.name());
    }

    public DeleteFileCommand setIds(Set<String> ids) {
        return with("ids", ids);
    }

    public DeleteFileCommand setBeforeTime(long beforeTime) {
        return with("beforeTime", beforeTime);
    }

    public DeleteFileCommand setOnlyDelTempFile(boolean onlyDelTempFile) {
        return with("onlyDelTempFile", onlyDelTempFile);
    }


    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(DeleteFileCommand.class);
    }

    public enum Type {
        /**
         * @see DeleteFileCommand#setIds(Set)
         */
        ids,
        /**
         * @see DeleteFileCommand#setBeforeTime(long)
         */
        beforeTime
    }

}
