package org.jetlinks.sdk.server.ai;

/**
 * @author gyl
 * @since 2.3
 */
public enum AiOutputMetadataType {
    /**
     * @see AiOutput#flat()
     */
    flat,
    /**
     * @see AiOutput#lightWeighFlat()
     */
    lightWeighFlat,
    /**
     * @see AiOutput#toLightWeighMap()
     */
    lightWeigh;
}
