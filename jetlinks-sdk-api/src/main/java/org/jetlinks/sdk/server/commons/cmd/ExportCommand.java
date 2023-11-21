package org.jetlinks.sdk.server.commons.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.PropertyMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.*;
import org.jetlinks.sdk.server.file.FileResponseCommand;
import org.jetlinks.sdk.server.utils.CastUtils;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 数据导出命令
 */
public class ExportCommand extends QueryCommand<Mono<FileResponseCommand.FileInfo>, ExportCommand> implements FileResponseCommand {
    public static final String FORMAT_JSON = "json";
    public static final String FORMAT_CSV = "csv";
    public static final String FORMAT_XLSX = "xlsx";

    public static final String PARAM_FORMAT = "format";

    public static final String PARAM_TEMPLATE = "template";

    //文件格式
    public String format(String defaultFormat) {
        return (String) readable().getOrDefault(PARAM_FORMAT, defaultFormat);
    }

    //是否为导入模版
    public boolean isTemplate() {
        return CastUtils.castBoolean(readable().getOrDefault(PARAM_TEMPLATE, false));
    }


    public static <T> CommandHandler<ExportCommand, Mono<FileResponseCommand.FileInfo>> createHandler(
            Consumer<SimpleFunctionMetadata> custom,
            Function<ExportCommand, Mono<FileResponseCommand.FileInfo>> handler) {

        return CommandHandler.of(
                () -> {
                    SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                    //Export
                    metadata.setId(CommandUtils.getCommandIdByType(ExportCommand.class));
                    metadata.setName(metadata.getId());
                    metadata.setName("导出数据");
                    metadata.setDescription("根据条件导出对应数据为指定格式文件");
                    metadata.setInputs(getParameterMetadata());
                    custom.accept(metadata);
                    return metadata;
                },
                (cmd, ignore) -> handler.apply(cmd),
                ExportCommand::new
        );

    }


    public static List<PropertyMetadata> getParameterMetadata() {
        return Arrays.asList(
                SimplePropertyMetadata.of(PARAM_FORMAT, "文件格式", new EnumType()
                        .addElement(EnumType.Element.of(FORMAT_XLSX, FORMAT_XLSX))
                        .addElement(EnumType.Element.of(FORMAT_CSV, FORMAT_CSV))
                        .addElement(EnumType.Element.of(FORMAT_JSON, FORMAT_JSON))
                ),
                SimplePropertyMetadata.of(PARAM_TEMPLATE, "是否只获取模版", BooleanType.GLOBAL),
                getTermsMetadata(),
                SimplePropertyMetadata.of("sorts", "排序", new ArrayType().elementType(
                        new ObjectType()
                                .addProperty("name", "列名(属性名)", StringType.GLOBAL)
                                .addProperty("order", "排序方式,如:asc,desc", StringType.GLOBAL)
                ))
        );
    }

}
