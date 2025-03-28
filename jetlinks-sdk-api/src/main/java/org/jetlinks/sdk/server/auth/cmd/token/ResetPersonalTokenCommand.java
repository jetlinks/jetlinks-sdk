package org.jetlinks.sdk.server.auth.cmd.token;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.sdk.server.commons.cmd.OperationByIdCommand;
import reactor.core.publisher.Mono;

@Schema(title = "重置私人令牌")
public class ResetPersonalTokenCommand extends OperationByIdCommand<Mono<Void>,ResetPersonalTokenCommand> {

}
