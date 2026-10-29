package org.jetlinks.sdk.server.auth.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 通过 authService 解绑指定维度实例的用户列表，类型、实例及用户不可为空。
 * 返回删除行数；角色服务返回成功处理的用户数。失败传播，不自动重试。
 * @since 1.1.1
 */
@Schema(title = "解绑维度用户")
public class UnbindDimensionUsersCommand extends AbstractCommand<Mono<Integer>, UnbindDimensionUsersCommand> {
    private static final long serialVersionUID = 1L;

    @Schema(description = "维度类型ID")
    public String getType() {
        return getOrNull("type", String.class);
    }

    public UnbindDimensionUsersCommand setType(String type) {
        return with("type", type);
    }

    @Schema(description = "维度实例ID")
    public String getId() {
        return getOrNull("id", String.class);
    }

    public UnbindDimensionUsersCommand setId(String id) {
        return with("id", id);
    }

    @Schema(description = "用户ID列表")
    public List<String> getUsers() {
        return ConverterUtils.convertToList(readable().get("users"), String.class::cast);
    }

    public UnbindDimensionUsersCommand setUsers(List<String> users) {
        return with("users", users);
    }
}
