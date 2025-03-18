package org.jetlinks.sdk.server.auth.cmd.token;


import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.auth.GrantScope;
import org.jetlinks.sdk.server.auth.PersonalToken;
import reactor.core.publisher.Mono;

@Schema(title = "申请私人令牌")
public class RequestPersonalTokenCommand extends AbstractCommand<Mono<PersonalToken>, RequestPersonalTokenCommand> {

    @Schema(title = "源类型", description = "用于标识业务类型,如: 大屏链接共享. api token等.")
    public String getSourceType() {
        return getOrNull("sourceType", String.class);
    }

    public RequestPersonalTokenCommand setSourceType(String sourceType) {
        return with("sourceType", sourceType);
    }

    @Schema(title = "源ID", description = "用于标识业务ID,如: 大屏ID 等.")
    public String getSourceId() {
        return getOrNull("sourceId", String.class);
    }

    public RequestPersonalTokenCommand setSourceId(String sourceId) {
        return with("sourceId", sourceId);
    }

    @Schema(title = "令牌描述")
    public String getDescription() {
        return getOrNull("description", String.class);
    }

    public RequestPersonalTokenCommand setDescription(String description) {
        return with("description", description);
    }

    @Schema(title = "重定向地址", description = "用于分享页面访问等场景 ")
    public String getUrl() {
        return getOrNull("url", String.class);
    }

    public RequestPersonalTokenCommand setUrl(String url) {
        return with("url", url);
    }

    @Schema(title = "令牌持有者")
    public String getOwnerId() {
        return getOrNull("ownerId", String.class);
    }

    public RequestPersonalTokenCommand setOwnerId(String ownerId) {
        return with("ownerId", ownerId);
    }

    @Schema(title = "授权范围", description = "授权范围,如: 维度,权限等.")
    public GrantScope getGrantScope() {
        return getOrNull("grantScope", GrantScope.class);
    }

    public RequestPersonalTokenCommand setGrantScope(GrantScope scope) {
        return with("grantScope", scope);
    }

    @Schema(title = "有效期(秒)",description = "小于0为永久有效.")
    public Long getExpires() {
        return getOrNull("expires", Long.class);
    }

    public RequestPersonalTokenCommand setExpires(Long expires) {
        return with("expires", expires);
    }


}
