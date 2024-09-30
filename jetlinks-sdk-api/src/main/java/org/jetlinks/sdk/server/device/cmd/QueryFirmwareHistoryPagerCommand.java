package org.jetlinks.sdk.server.device.cmd;

import org.hswebframework.web.api.crud.entity.PagerResult;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.sdk.server.commons.cmd.QueryPagerCommand;
import org.jetlinks.sdk.server.device.FirmwareUpgradeHistoryInfo;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * 分页获取固件升级记录.
 *
 * @author zhangji 2024/9/30
 * @since 2.3
 */
public class QueryFirmwareHistoryPagerCommand extends QueryPagerCommand<FirmwareUpgradeHistoryInfo> {

    private static final long serialVersionUID = 5459082740275053158L;

    public static CommandHandler<QueryFirmwareHistoryPagerCommand, Mono<PagerResult<FirmwareUpgradeHistoryInfo>>> createHandler(
        Function<QueryFirmwareHistoryPagerCommand, Mono<PagerResult<FirmwareUpgradeHistoryInfo>>> handler) {
        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(QueryFirmwareHistoryPagerCommand.class));
                metadata.setName("分页获取固件升级记录");
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            QueryFirmwareHistoryPagerCommand::new
        );
    }
}