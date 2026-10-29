package org.jetlinks.sdk.server.auth.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.auth.UserSaveRequest;
import reactor.core.publisher.Mono;

/**
 * 更新指定用户及关联关系，返回用户ID；必须具有用户维护及目标用户资产权限。
 * 通过 authService:user 执行，沿用调用者认证及资产范围；不自动重试。
 * @since 1.1.1
 */
@Schema(title = "修改用户")
public class UpdateUserCommand extends AbstractCommand<Mono<String>, UpdateUserCommand> {
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    public String getId() {
        return getOrNull("id", String.class);
    }

    public UpdateUserCommand setId(String id) {
        return with("id", id);
    }

    @Schema(description = "用户及关联信息")
    public UserSaveRequest getRequest() {
        return getOrNull("request", UserSaveRequest.class);
    }

    public UpdateUserCommand setRequest(UserSaveRequest request) {
        return with("request", request);
    }
}
