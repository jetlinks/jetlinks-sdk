package org.jetlinks.sdk.server.ai.cv;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.sdk.server.ai.AiOutput;
import org.jetlinks.sdk.server.ai.GenericAiOutput;

/**
 * @author gyl
 * @since 1.0.1
 */
@Getter
@Setter
public class AiCommandResult<SELF extends AiCommandResult<SELF>> extends GenericAiOutput<SELF> implements AiOutput<SELF> {


}
