package org.jetlinks.sdk.server.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AI 服务提供商
 *
 * @since 1.0.3
 */
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class AiProvider {

    private String id;
    private String name;

}
