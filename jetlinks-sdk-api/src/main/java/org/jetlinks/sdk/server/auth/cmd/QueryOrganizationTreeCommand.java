package org.jetlinks.sdk.server.auth.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.sdk.server.auth.OrganizationInfo;
import org.jetlinks.sdk.server.commons.cmd.QueryListCommand;
import reactor.core.publisher.Flux;

import java.util.function.Function;

/**
 * 查询组织树结构列表.
 *
 * @author zhangji 2024/2/2
 * @since 1.0.0
 */
public class QueryOrganizationTreeCommand extends QueryListCommand<OrganizationInfo> {
    private static final long serialVersionUID = 1L;

    public static CommandHandler<QueryOrganizationTreeCommand, Flux<OrganizationInfo>> createHandler(
        Function<QueryOrganizationTreeCommand, Flux<OrganizationInfo>> handler
    ) {
        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(QueryOrganizationTreeCommand.class));
                metadata.setName(metadata.getId());
                metadata.setName("查询组织树结构列表");
                metadata.setDescription("可指定查询条件，排序规则等");
                metadata.setInputs(getQueryParamMetadata());
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            QueryOrganizationTreeCommand::new
        );

    }
}
