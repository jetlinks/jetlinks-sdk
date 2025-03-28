package org.jetlinks.sdk.server.auth.cmd.token;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.sdk.server.auth.PersonalToken;
import org.jetlinks.sdk.server.commons.cmd.QueryListCommand;

@Schema(title = "查询私人令牌配置信息")
public class QueryPersonalTokenCommand extends QueryListCommand<PersonalToken> {

}
