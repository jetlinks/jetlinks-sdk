package org.jetlinks.sdk.server.ai;

import org.jetlinks.core.command.Command;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.DataType;
import org.jetlinks.core.metadata.PropertyMetadata;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.utils.MetadataUtils;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;

/**
 * AI命令,实现此接口,标记命令为AI相关的命令
 *
 * @param <R> 命令返回类型
 * @see AiDomain
 */
public interface AiCommand<R extends AiOutput> extends Command<Flux<R>> {


    /**
     * 获取响应类型R为模型描述
     * <pre>扫描响应类型{@code @Schema(title = "属性名")}</pre>
     */
    default List<PropertyMetadata> getMetadata() {
        return getCommandOutputMetadata(getClass());
    }

    /**
     * 获取持久化数据map的模型描述
     */
    default List<PropertyMetadata> getLightWeighMapMetadata() {
        return getMetadata();
    }


    /**
     * 获取平铺数据map的模型描述
     */
    default List<PropertyMetadata> getFlatMapMetadata() {
        return getMetadata();
    }


    /**
     * 获取命令的响应类型R为模型描述，扫描参数属性注释
     */
    default <T extends Command<?>> List<PropertyMetadata> getCommandOutputMetadata(Class<T> aclass) {
        ResolvableType responseDataType = CommandUtils.getCommandResponseDataType(aclass);
        return getClassMetadata(responseDataType);
    }

    /**
     * 扫描参数属性注释
     * <pre>{@code @Schema(title = "属性名")}</pre>
     */
    default List<PropertyMetadata> getClassMetadata(ResolvableType responseDataType) {
        DataType dataType = MetadataUtils.parseType(responseDataType);
        if (dataType instanceof ObjectType) {
            return ((ObjectType) dataType).getProperties();
        }
        return Collections.emptyList();
    }

}
