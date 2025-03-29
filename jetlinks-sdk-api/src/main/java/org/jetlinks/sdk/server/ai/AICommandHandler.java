package org.jetlinks.sdk.server.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetlinks.core.command.Command;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandSupport;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.DataType;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.PropertyMetadata;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.utils.MetadataUtils;
import org.springframework.core.ResolvableType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author gyl
 * @since 2.3
 */
@AllArgsConstructor
public class AICommandHandler<C extends Command<R>, R> implements CommandHandler<C, R> {

    private final Supplier<FunctionMetadata> description;

    private final BiFunction<C, CommandSupport, R> handler;

    private final Supplier<C> commandBuilder;

    /**
     * @see AiOutput#toLightWeighMap()
     */
    @Getter
    private final Collection<PropertyMetadata> lightWeightProperties;

    /**
     * @see AiOutput#flat()
     */
    @Getter
    private final Collection<PropertyMetadata> flatProperties;

    /**
     * @see AiOutput#lightWeighFlat()
     */
    @Getter
    private final Collection<PropertyMetadata> flatLightWeightProperties;


    @SuppressWarnings("all")
    public static <R, C extends Command<R>> AICommandHandler<C, R> of(Supplier<FunctionMetadata> metadata,
                                                                      BiFunction<C, CommandSupport, R> executor,
                                                                      Supplier<C> commandBuilder,
                                                                      AiOutputMetadataBuilder lightWeightProperties,
                                                                      AiOutputMetadataBuilder flatProperties,
                                                                      AiOutputMetadataBuilder flatLightWeightProperties) {
        Class<C> commandClass = (Class<C>) commandBuilder.get().getClass();
        return of(metadata, executor, commandBuilder,
                  lightWeightProperties.generateMetadata(commandClass),
                  flatProperties.generateMetadata(commandClass),
                  flatLightWeightProperties.generateMetadata(commandClass));
    }


    @SuppressWarnings("all")
    public static <R, C extends Command<R>> AICommandHandler<C, R> of(Supplier<FunctionMetadata> metadata,
                                                                      BiFunction<C, CommandSupport, R> executor,
                                                                      Supplier<C> commandBuilder,
                                                                      AiOutputMetadataBuilder lightWeightProperties,
                                                                      AiOutputMetadataBuilder flatProperties) {
        Class<C> commandClass = (Class<C>) commandBuilder.get().getClass();
        return of(metadata, executor, commandBuilder,
                  lightWeightProperties.generateMetadata(commandClass),
                  flatProperties.generateMetadata(commandClass));
    }


    @SuppressWarnings("all")
    public static <R, C extends Command<R>> AICommandHandler<C, R> of(Supplier<FunctionMetadata> metadata,
                                                                      BiFunction<C, CommandSupport, R> executor,
                                                                      Supplier<C> commandBuilder,
                                                                      AiOutputMetadataBuilder lightWeightProperties) {
        Class<C> commandClass = (Class<C>) commandBuilder.get().getClass();
        return of(metadata, executor, commandBuilder,
                  lightWeightProperties.generateMetadata(commandClass));
    }


    public static <R, C extends Command<R>> AICommandHandler<C, R> of(Supplier<FunctionMetadata> metadata,
                                                                      BiFunction<C, CommandSupport, R> executor,
                                                                      Supplier<C> commandBuilder,
                                                                      ResolvableType lightWeightProperties,
                                                                      ResolvableType flatProperties,
                                                                      ResolvableType flatLightWeightProperties) {
        return of(metadata, executor, commandBuilder,
                  AICommandHandler.getClassMetadata(lightWeightProperties),
                  AICommandHandler.getClassMetadata(flatProperties),
                  AICommandHandler.getClassMetadata(flatLightWeightProperties));
    }


    public static <R, C extends Command<R>> AICommandHandler<C, R> of(Supplier<FunctionMetadata> metadata,
                                                                      BiFunction<C, CommandSupport, R> executor,
                                                                      Supplier<C> commandBuilder,
                                                                      ResolvableType lightWeightProperties,
                                                                      ResolvableType flatProperties) {
        return of(metadata, executor, commandBuilder,
                  AICommandHandler.getClassMetadata(lightWeightProperties),
                  AICommandHandler.getClassMetadata(flatProperties));
    }


    public static <R, C extends Command<R>> AICommandHandler<C, R> of(Supplier<FunctionMetadata> metadata,
                                                                      BiFunction<C, CommandSupport, R> executor,
                                                                      Supplier<C> commandBuilder,
                                                                      ResolvableType lightWeightProperties) {
        return of(metadata, executor, commandBuilder,
                  AICommandHandler.getClassMetadata(lightWeightProperties));
    }

    /**
     * 构建自定义ai命令
     *
     * @param metadata                  ai命令模型解析
     * @param executor                  执行逻辑
     * @param commandBuilder            ai命令
     * @param lightWeightProperties     轻量响应模型
     * @param flatProperties            平铺响应模型
     * @param flatLightWeightProperties 平铺轻量响应模型
     * @param <C>                       ai命令
     * @param <R>                       响应
     */
    public static <R, C extends Command<R>> AICommandHandler<C, R> of(Supplier<FunctionMetadata> metadata,
                                                                      BiFunction<C, CommandSupport, R> executor,
                                                                      Supplier<C> commandBuilder,
                                                                      Collection<PropertyMetadata> lightWeightProperties,
                                                                      Collection<PropertyMetadata> flatProperties,
                                                                      Collection<PropertyMetadata> flatLightWeightProperties) {
        return new AICommandHandler<>(metadata, executor, commandBuilder, lightWeightProperties, flatProperties, flatLightWeightProperties);
    }

    /**
     * 构建自定义ai命令，平铺轻量响应默认使用平铺模型
     */
    public static <R, C extends Command<R>> AICommandHandler<C, R> of(Supplier<FunctionMetadata> metadata,
                                                                      BiFunction<C, CommandSupport, R> executor,
                                                                      Supplier<C> commandBuilder,
                                                                      Collection<PropertyMetadata> lightWeightProperties,
                                                                      Collection<PropertyMetadata> flatProperties) {
        return new AICommandHandler<>(metadata, executor, commandBuilder, lightWeightProperties, flatProperties, flatProperties);
    }

    /**
     * 构建自定义ai命令，所有特殊响应默认使用轻量模型
     */
    public static <R, C extends Command<R>> AICommandHandler<C, R> of(Supplier<FunctionMetadata> metadata,
                                                                      BiFunction<C, CommandSupport, R> executor,
                                                                      Supplier<C> commandBuilder,
                                                                      Collection<PropertyMetadata> properties) {
        return new AICommandHandler<>(metadata, executor, commandBuilder, properties, properties, properties);
    }


    public Collection<PropertyMetadata> getAiOutputPropertyMetadata(AiOutputMetadataType type) {
        switch (type) {
            case flat:
                return flatProperties;
            case lightWeighFlat:
                return flatLightWeightProperties;
            case lightWeigh:
                return lightWeightProperties;
        }
        return Collections.emptyList();
    }

    @Override
    public R handle(@Nonnull C command, @Nonnull CommandSupport support) {
        return handler.apply(command, support);
    }

    @Nonnull
    @Override
    public C createCommand() {
        return commandBuilder.get();
    }

    @Override
    public FunctionMetadata getMetadata() {
        return description.get();
    }


    /**
     * 获取命令的响应类型R为模型描述，扫描参数属性注释
     * <pre>{@code @Schema(title = "属性名")}</pre>
     */
    public static <T extends Command<?>> List<PropertyMetadata> getCommandOutputMetadata(Class<T> aclass) {
        ResolvableType responseDataType = CommandUtils.getCommandResponseDataType(aclass);
        return getClassMetadata(responseDataType);
    }

    /**
     * 扫描类中参数属性注释
     * <pre>{@code @Schema(title = "属性名")}</pre>
     */
    public static List<PropertyMetadata> getClassMetadata(ResolvableType responseDataType) {
        DataType dataType = MetadataUtils.parseType(responseDataType);
        if (dataType instanceof ObjectType) {
            return ((ObjectType) dataType).getProperties();
        }
        return Collections.emptyList();
    }


}
