package org.jetlinks.sdk.server.device.cmd;

import org.hswebframework.web.api.crud.entity.PagerResult;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.sdk.server.commons.cmd.QueryPagerCommand;
import org.jetlinks.sdk.server.device.FirmwareInfo;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * 分页获取固件与升级记录信息.
 *
 * @author zhangji 2024/9/29
 * @since 2.3
 */
public class QueryFirmwareInfoPagerCommand extends QueryPagerCommand<FirmwareInfo> {

    public static CommandHandler<QueryFirmwareInfoPagerCommand, Mono<PagerResult<FirmwareInfo>>> createHandler(
        Function<QueryFirmwareInfoPagerCommand, Mono<PagerResult<FirmwareInfo>>> handler) {
        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(QueryFirmwareInfoPagerCommand.class));
                metadata.setName("分页获取固件与升级记录信息");
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            QueryFirmwareInfoPagerCommand::new
        );
    }
}
