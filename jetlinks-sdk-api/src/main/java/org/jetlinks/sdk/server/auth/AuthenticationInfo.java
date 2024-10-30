package org.jetlinks.sdk.server.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.authorization.Dimension;
import org.hswebframework.web.authorization.simple.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户权限信息.
 *
 * @author zhangji 2024/10/29
 * @since 2.3
 */
@Getter
@Setter
public class AuthenticationInfo {

    @Schema(description = "用户信息")
    private SimpleUser user;

    @Schema(description = "权限列表")
    private List<SimplePermission> permissions = new ArrayList<>();

    @Schema(description = "维度列表")
    private List<DimensionInfo> dimensions = new ArrayList<>();

    @Schema(description = "其他配置")
    private Map<String, Serializable> attributes = new HashMap<>();

    public Mono<SimpleAuthentication> toAuthentication() {
        return this
            .toDimensions()
            .collectList()
            .map(dimension -> {
                SimpleAuthentication authentication = new SimpleAuthentication();
                authentication.setUser(user);
                authentication.setPermissions(new ArrayList<>(permissions));
                authentication.setDimensions(dimension);
                authentication.setAttributes(attributes);
                return authentication;
            });
    }

    private Flux<Dimension> toDimensions() {
        return Flux
            .fromIterable(dimensions)
            .mapNotNull(dimension -> SimpleDimension.of(
                dimension.getId(),
                dimension.getName(),
                SimpleDimensionType.of(dimension.getType()),
                dimension.getOptions()));
    }

    @Getter
    @Setter
    public static class DimensionInfo {

        @Schema(description = "维度ID")
        private String id;

        @Schema(description = "维度名称")
        private String name;

        @Schema(description = "维度类型")
        private String type;

        @Schema(description = "其他配置")
        private Map<String, Object> options;
    }

}
