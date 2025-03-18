package org.jetlinks.sdk.server.auth.cmd.token;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.sdk.server.auth.PersonalToken;
import org.jetlinks.sdk.server.commons.cmd.DeleteByIdCommand;

@Schema(title = "删除私人令牌")
public class RemovePersonalTokenCommand extends DeleteByIdCommand<PersonalToken> {

}
