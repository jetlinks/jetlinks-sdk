package org.jetlinks.sdk.server.auth.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.auth.UserSaveRequest;
import reactor.core.publisher.Mono;

/**
 * 创建用户及关联角色、组织，返回用户ID；指定ID已存在时失败，不覆盖已有账号。
 * 通过 authService:user 执行，沿用调用者认证及资产范围；不自动重试。
 * @since 1.1.1
 */
@Schema(title = "创建用户")
public class CreateUserCommand extends AbstractCommand<Mono<String>, CreateUserCommand> {
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户及关联信息")
    public UserSaveRequest getRequest() {
        return getOrNull("request", UserSaveRequest.class);
    }

    public CreateUserCommand setRequest(UserSaveRequest request) {
        return with("request", request);
    }
}
