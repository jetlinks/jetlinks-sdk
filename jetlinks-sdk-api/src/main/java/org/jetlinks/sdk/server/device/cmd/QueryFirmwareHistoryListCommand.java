package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.sdk.server.commons.cmd.QueryListCommand;
import org.jetlinks.sdk.server.device.FirmwareUpgradeHistoryInfo;
import reactor.core.publisher.Flux;

import java.util.function.Function;

/**
 * 获取固件升级记录列表.
 *
 * @author zhangji 2024/9/30
 * @since 2.3
 */
public class QueryFirmwareHistoryListCommand extends QueryListCommand<FirmwareUpgradeHistoryInfo> {

    private static final long serialVersionUID = -5259476496199864182L;

    public static CommandHandler<QueryFirmwareHistoryListCommand, Flux<FirmwareUpgradeHistoryInfo>> createHandler(
        Function<QueryFirmwareHistoryListCommand, Flux<FirmwareUpgradeHistoryInfo>> handler) {
        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(QueryFirmwareHistoryListCommand.class));
                metadata.setName("获取固件升级记录列表");
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            QueryFirmwareHistoryListCommand::new
        );
    }
}