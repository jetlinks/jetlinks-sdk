package org.jetlinks.sdk.server.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.BooleanType;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.utils.CastUtils;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.concurrent.Queues;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * 根据模板保存数据
 *
 * @author gyl
 * @since 1.0.1
 */
@Slf4j
@Schema(title = "根据模板保存数据")
public class SaveByTemplateCommand extends AbstractConvertCommand<Flux<SaveByTemplateResult>, SaveByTemplateCommand> {


    public static final String PARAMETER_KEY = "data";

    public final SaveByTemplateCommand withTemplate(List<EntityTemplateInfo> entityInstances) {

        writable().put(PARAMETER_KEY, entityInstances);

        return castSelf();
    }

    @Schema(title = "模板数据")
    public final List<EntityTemplateInfo> getData() {
        return ConverterUtils.convertToList(readable().get(PARAMETER_KEY), EntityTemplateInfo::of);
    }

    @Deprecated
    public final List<EntityTemplateInfo> templateList() {
        return getData();
    }

    public final <E> List<E> templateList(Function<EntityTemplateInfo, E> converter) {
        return ConverterUtils.convertToList(readable().get(PARAMETER_KEY), f -> converter.apply(EntityTemplateInfo.of(f)));
    }

    @Schema(title = "是否抛出错误")
    public boolean isThrowError() {
        return CastUtils.castBoolean(readable().getOrDefault("throwError", true));
    }

    public SaveByTemplateCommand setThrowError(boolean throwError) {
        return with("throwError", throwError);
    }

    public static CommandHandler<SaveByTemplateCommand, Flux<SaveByTemplateResult>> createHandler(
        Function<SaveByTemplateCommand, Flux<SaveByTemplateResult>> handler) {
        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(SaveByTemplateCommand.class));
                metadata.setName("根据模板保存数据");
                metadata.setInputs(Arrays.asList(
                    SimplePropertyMetadata.of("data", "模板数据", EntityTemplateInfo.parseMetadata()),
                    SimplePropertyMetadata.of("throwError", "抛出错误", BooleanType.GLOBAL)
                ));
                metadata.setOutput(new ObjectType()
                                       .addProperty("id", "id", StringType.GLOBAL)
                                       .addProperty("success", "是否成功", BooleanType.GLOBAL)
                                       .addProperty("errorMessage", "错误信息", StringType.GLOBAL)
                                       .addProperty("data", "data", new ObjectType()));
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            SaveByTemplateCommand::new
        );
    }


    public <T> Flux<SaveByTemplateResult> execute(Function<EntityTemplateInfo, Mono<T>> converter,
                                                  Function<Flux<DataContext<T>>, Mono<Void>> handler) {
        return execute(converter, handler, Queues.SMALL_BUFFER_SIZE);
    }


    /**
     * 执行模板转换并处理对应数据,此方法会自动填充返回处理结果。
     *
     * @param converter         转换模板为数据
     * @param handler           处理转化成功的数据
     * @param converterParallel 并行度
     * @param <T>               数据
     * @return
     */
    public <T> Flux<SaveByTemplateResult> execute(Function<EntityTemplateInfo, Mono<T>> converter,
                                                  Function<Flux<DataContext<T>>, Mono<Void>> handler,
                                                  int converterParallel) {
        return Flux
            .fromIterable(getData())
            .flatMap(info -> converter
                .apply(info)
                .map(data -> DataContext.simple(info, data))
                .onErrorResume(error -> {
                    if (isThrowError()) {
                        return Mono.error(error);
                    }
                    //报错转换结果
                    DataContext<T> errContext = DataContext.simple(info, null);
                    errContext.error(error);
                    log.warn("converter data by template fail", error);
                    return Mono.just(errContext);
                }), converterParallel)
            .as(flux -> {
                Flux<DataContext<T>> cache = flux.cache();
                return handler
                    //仅处理有数据的
                    .apply(cache.filter(c -> c.getData() != null))
                    //报错转换结果
                    .onErrorResume(err -> {
                        if (isThrowError()) {
                            return Mono.error(err);
                        }
                        return cache
                            .doOnNext(d -> d.error(err))
                            .then(Mono.empty());
                    })
                    .thenMany(cache.map(DataContext::toResult));
            });
    }

    @Getter
    @Setter
    static class SimpleDataContext<T> implements DataContext<T> {
        @Nullable
        private T data;
        private EntityTemplateInfo info;
        private boolean success = true;
        private String errorMessage;

        public SimpleDataContext(EntityTemplateInfo info, @Nullable T data) {
            this.info = info;
            this.data = data;
        }

        @Override
        public void success() {
            success = true;
        }

        @Override
        public void error(Throwable error) {
            success = false;
            errorMessage = error.getLocalizedMessage();
        }

        @Override
        public void error(String message) {
            success = false;
            errorMessage = message;
        }

        @Override
        public SaveByTemplateResult toResult() {
            SaveByTemplateResult response = SaveByTemplateResult.of(info);
            response.setDate(data);
            response.setSuccess(success);
            response.setErrorMessage(errorMessage);
            return response;
        }
    }


    public interface DataContext<T> {
        T getData();

        /**
         * 标记此数据处理成功
         */
        void success();

        /**
         * 标记此数据处理失败
         */
        void error(Throwable error);

        void error(String message);

        SaveByTemplateResult toResult();

        static <D> DataContext<D> simple(EntityTemplateInfo info, D data) {
            return new SimpleDataContext<>(info, data);
        }

    }


}
