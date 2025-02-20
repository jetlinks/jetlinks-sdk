package org.jetlinks.sdk.server.collector.cmd;

import org.hswebframework.web.api.crud.entity.PagerResult;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.core.metadata.PropertyMetadata;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.sdk.server.collector.CollectorInfo;
import org.jetlinks.sdk.server.commons.cmd.QueryPagerCommand;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

/**
 * @author wangsheng
 */
public class QueryCollectorPagerCommand extends QueryPagerCommand<CollectorInfo> {
    
    private static final long serialVersionUID = 532554566345817419L;
    
    final static String DRIVER_IDS = "driverIds";
    
    public QueryCollectorPagerCommand withDriverIds(List<String> driverIds) {
        with(DRIVER_IDS, driverIds);
        return this;
    }
    
    public List<String> getDriverIds() {
        return ConverterUtils
            .convertToList(readable().get(DRIVER_IDS), String::valueOf);
    }
    
    public static FunctionMetadata metadata() {
        SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
        metadata.setId(CommandUtils.getCommandIdByType(QueryCollectorPagerCommand.class));
        metadata.setName("根据采集器插件分页查询采集器信息");
        List<PropertyMetadata> properties = getQueryParamMetadata();
        properties.add(SimplePropertyMetadata.of("driverIds", "数采插件ID集合", new ArrayType()));
        metadata.setInputs(properties);
        return metadata;
    }
    
    public static CommandHandler<QueryCollectorPagerCommand, Mono<PagerResult<CollectorInfo>>> createHandler(Function<QueryCollectorPagerCommand, Mono<PagerResult<CollectorInfo>>> handler) {
        return CommandHandler.of(
            metadata(),
            (cmd, ignore) -> handler.apply(cmd),
            QueryCollectorPagerCommand::new
        );
    }
}
