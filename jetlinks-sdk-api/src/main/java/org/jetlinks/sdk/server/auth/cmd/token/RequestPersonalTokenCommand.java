package org.jetlinks.sdk.server.auth.cmd.token;


import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.sdk.server.auth.GrantScope;
import org.jetlinks.sdk.server.auth.PersonalToken;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

@Schema(title = "申请私人令牌")
public class RequestPersonalTokenCommand extends AbstractCommand<Mono<PersonalToken>, RequestPersonalTokenCommand> {

    @Schema(title = "源类型", description = "用于标识业务类型,如: 大屏链接共享. api token等.")
    public String getSourceType() {
        return getOrNull("sourceType", String.class);
    }

    public RequestPersonalTokenCommand setSourceType(String sourceType) {
        return with("sourceType", sourceType);
    }


    @Schema(title = "源类型名称", description = "用于描述业务类型")
    public String getSourceTypeName() {
        return getOrNull("sourceTypeName", String.class);
    }

    public RequestPersonalTokenCommand setSourceTypeName(String sourceTypeName) {
        return with("sourceTypeName", sourceTypeName);
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
    public String getRedirect() {
        return getOrNull("redirect", String.class);
    }

    public RequestPersonalTokenCommand setRedirect(String url) {
        return with("redirect", url);
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

    @Schema(title = "认证类型", description = "认证类型,用于获取token时的二次认证")
    public String getAuthType() {
        return getOrNull("authType", String.class);
    }

    public RequestPersonalTokenCommand setAuthType(String authType) {
        return with("authType", authType);
    }

    @Schema(title = "认证配置", description = "认证配置,用于获取token时的二次认证")
    public Map<String, Object> getAuthConfiguration() {
        @SuppressWarnings("all")
        Map<String, Object> map = getOrNull("authConfiguration", Map.class);
        return map == null ? Collections.emptyMap() : map;
    }

    public RequestPersonalTokenCommand setAuthConfiguration(Map<String, Object> authConfiguration) {
        return with("authConfiguration", authConfiguration);
    }


}
