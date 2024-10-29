package org.jetlinks.sdk.server.auth.cmd;

import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.auth.AuthenticationInfo;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.function.Function;

/**
 * 生成token.
 *
 * @author zhangji 2024/10/26
 * @since 2.3
 */
public class GenerateUserTokenCommand extends AbstractCommand<Mono<String>, GenerateUserTokenCommand> {

    private static final long serialVersionUID = 1L;

    /**
     * 当前用户ID
     * @return 用户ID
     */
    public String getUserId() {
        return getOrNull("userId", String.class);
    }

    public GenerateUserTokenCommand withUserId(String userId) {
        return with("userId", userId);
    }

    /**
     * token过期时间，单位毫秒
     * @return 过期时间
     */
    public Long getExpires() {
        return getOrNull("expires", Long.class);
    }

    public GenerateUserTokenCommand withExpires(Long expires) {
        return with("expires", expires);
    }

    /**
     * 指定token的用户权限
     * @return 用户权限
     */
    public AuthenticationInfo getAuthentication() {
        return getOrNull("authentication", AuthenticationInfo.class);
    }

    public GenerateUserTokenCommand withAuthentication(AuthenticationInfo authentication) {
        return with("authentication", authentication);
    }

    public static CommandHandler<GenerateUserTokenCommand, Mono<String>> createHandler(
        Function<GenerateUserTokenCommand, Mono<String>> handler
    ) {
        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(GetDimensionUserBindCommand.class));
                metadata.setName("根据用户id生成token");
                metadata.setDescription("");
                metadata.setInputs(
                    Collections.singletonList(SimplePropertyMetadata.of("userId", "用户ID", StringType.GLOBAL))
                );
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            GenerateUserTokenCommand::new
        );
    }
}
