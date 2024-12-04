package org.jetlinks.sdk.server.commons.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.core.command.GenericInputCommand;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * 批量数据操作指令
 *
 * @param <T>    数据类型
 * @param <Self> Self
 * @author zhouhao
 * @since 1.0
 */
@Getter
@Setter
public abstract class BatchDataCommand<T, Self extends BatchDataCommand<T, Self>> extends AbstractConvertCommand<Flux<T>, Self>
implements GenericInputCommand<T> {

    public static final String PARAMETER_KEY = "data";

    @SafeVarargs
    public final Self withData(T... entityInstances) {
        return withData(Arrays.asList(entityInstances));
    }

    public final Self withData(List<? extends T> entityInstances) {

        writable().put(PARAMETER_KEY, entityInstances);

        return castSelf();
    }

    @SuppressWarnings("all")
    public final <E extends T> List<E> dataList() {
        return dataList(v -> (E) createResponseData(v));
    }

    public final <E extends T> List<E> dataList(Function<Object, E> converter) {
        return ConverterUtils.convertToList(readable().get(PARAMETER_KEY), converter);
    }

    @Getter
    @Setter
    protected static class InputSpec<T> implements GenericInputCommand.InputSpec<T> {
        @Schema(title = "数据")
        private List<T> data;
    }

}
