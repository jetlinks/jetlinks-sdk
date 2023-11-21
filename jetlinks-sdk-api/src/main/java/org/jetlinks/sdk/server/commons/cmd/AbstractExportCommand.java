package org.jetlinks.sdk.server.commons.cmd;

import org.jetlinks.core.metadata.PropertyMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.*;
import org.jetlinks.sdk.server.utils.CastUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 数据导出命令
 */
public abstract class AbstractExportCommand<T, Self extends AbstractExportCommand<T, Self>> extends QueryCommand<T, Self> {
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
