package org.jetlinks.sdk.server.auth.cmd;

import org.hswebframework.web.api.crud.entity.PagerResult;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.sdk.server.auth.UserDetail;
import org.jetlinks.sdk.server.commons.cmd.QueryPagerCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * 分页查询用户详情.
 *
 * @author zhangji 2024/2/2
 * @since 1.0.0
 */
public class QueryUserDetailPageCommand extends QueryPagerCommand<UserDetail> {
    private static final long serialVersionUID = 1L;

    public static CommandHandler<QueryUserDetailPageCommand, Mono<PagerResult<UserDetail>>> createHandler(
        Function<QueryUserDetailPageCommand, Mono<PagerResult<UserDetail>>> handler
    ) {
        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(QueryUserDetailPageCommand.class));
                metadata.setName("分页查询用户详情");
                metadata.setDescription("可指定查询条件，分页参数，排序规则等");
                metadata.setInputs(getQueryParamMetadata());
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            QueryUserDetailPageCommand::new
        );
    }
}
