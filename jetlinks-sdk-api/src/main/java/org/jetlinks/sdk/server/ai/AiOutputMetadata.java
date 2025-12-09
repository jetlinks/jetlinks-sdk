package org.jetlinks.sdk.server.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetlinks.core.command.Command;
import org.jetlinks.core.metadata.PropertyMetadata;
import org.springframework.core.ResolvableType;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AiOutputMetadata {

    /**
     * @see AiOutput#toLightWeighMap()
     */
    private Collection<PropertyMetadata> lightWeightProperties;

    /**
     * @see AiOutput#flat()
     */
    private Collection<PropertyMetadata> flatProperties;

    /**
     * @see AiOutput#lightWeighFlat()
     */
    private Collection<PropertyMetadata> flatLightWeightProperties;


    public Collection<PropertyMetadata> getAiOutputPropertyMetadata(AiOutputMetadataType type) {
        return switch (type) {
            case flat -> flatProperties;
            case lightWeighFlat -> flatLightWeightProperties;
            case lightWeigh -> lightWeightProperties;
        };
    }

    public static AiOutputMetadata of(Collection<PropertyMetadata> lightWeightProperties) {
        return new AiOutputMetadata(lightWeightProperties, lightWeightProperties, lightWeightProperties);
    }

    public static AiOutputMetadata of(Collection<PropertyMetadata> lightWeightProperties,
                                      Collection<PropertyMetadata> flatProperties) {
        return new AiOutputMetadata(lightWeightProperties, flatProperties, flatProperties);
    }

    public static AiOutputMetadata of(Collection<PropertyMetadata> lightWeightProperties,
                                      Collection<PropertyMetadata> flatProperties,
                                      Collection<PropertyMetadata> flatLightWeightProperties) {
        return new AiOutputMetadata(lightWeightProperties, flatProperties, flatLightWeightProperties);
    }


    public static AiOutputMetadata of(ResolvableType lightWeightProperties,
                                      ResolvableType flatProperties,
                                      ResolvableType flatLightWeightProperties) {
        return of(AICommandHandler.getClassMetadata(lightWeightProperties),
                  AICommandHandler.getClassMetadata(flatProperties),
                  AICommandHandler.getClassMetadata(flatLightWeightProperties));
    }

    public static AiOutputMetadata of(ResolvableType lightWeightProperties,
                                      ResolvableType flatProperties) {
        return of(AICommandHandler.getClassMetadata(lightWeightProperties),
                  AICommandHandler.getClassMetadata(flatProperties));
    }

    public static AiOutputMetadata of(ResolvableType lightWeightProperties) {
        return of(AICommandHandler.getClassMetadata(lightWeightProperties));
    }


    public static AiOutputMetadata of(Class<Command<?>> commandClass,
                                      AiOutputMetadataBuilder lightWeightProperties,
                                      AiOutputMetadataBuilder flatProperties,
                                      AiOutputMetadataBuilder flatLightWeightProperties) {
        return of(lightWeightProperties.generateMetadata(commandClass),
                  flatProperties.generateMetadata(commandClass),
                  flatLightWeightProperties.generateMetadata(commandClass));
    }

    public static AiOutputMetadata of(Class<Command<?>> commandClass,
                                      AiOutputMetadataBuilder lightWeightProperties,
                                      AiOutputMetadataBuilder flatProperties) {
        return of(lightWeightProperties.generateMetadata(commandClass),
                  flatProperties.generateMetadata(commandClass));
    }

    public static AiOutputMetadata of(Class<Command<?>> commandClass,
                                      AiOutputMetadataBuilder lightWeightProperties) {
        return of(lightWeightProperties.generateMetadata(commandClass));
    }


}
