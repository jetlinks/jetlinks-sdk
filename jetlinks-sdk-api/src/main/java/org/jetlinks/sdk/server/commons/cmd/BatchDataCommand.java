package org.jetlinks.sdk.server.commons.cmd;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class BatchDataCommand<T, Self extends BatchDataCommand<T, Self>> extends AbstractCommand<Flux<T>, Self> {

    public static final String PARAMETER_KEY = "data";

    @SafeVarargs
    public final Self withData(T... entityInstances) {
        return withData(Arrays.asList(entityInstances));
    }

    public final Self withData(List<? extends T> entityInstances) {

        writable().put(PARAMETER_KEY, entityInstances);

        return castSelf();
    }

    public final <E extends T> List<E> dataList(Function<Object, E> converter) {
        Object entities = readable().get(PARAMETER_KEY);
        if (entities == null) {
            return Collections.emptyList();
        }

        if (entities instanceof Collection) {
            return ((Collection<?>) entities)
                    .stream()
                    .map(converter)
                    .collect(Collectors.toList());
        }
        return Collections.singletonList(converter.apply(entities));
    }


}
