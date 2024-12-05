package org.jetlinks.sdk.server.commons.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.springframework.core.ResolvableType;

import java.util.function.Function;

/**
 * @author wangsheng
 */
@Schema(description = "批量创建数据并激活")
public class SaveAndEnableCommand<T> extends BatchDataCommand<T, SaveAndEnableCommand<T>> {

    public static <T> SaveAndEnableCommand<T> of(Function<Object, T> converter) {
        return new SaveAndEnableCommand<T>().withConverter(converter);
    }

    public static <T> SaveAndEnableCommand<T> of(Class<T> type) {
        return of(CommandUtils.createConverter(ResolvableType.forClass(type)));
    }

    public static FunctionMetadata metadata(Class<?> dataType) {
        return CommandMetadataResolver.resolve(
            ResolvableType
                .forClassWithGenerics(
                    SaveAndEnableCommand.class, dataType
                ));
    }
}
