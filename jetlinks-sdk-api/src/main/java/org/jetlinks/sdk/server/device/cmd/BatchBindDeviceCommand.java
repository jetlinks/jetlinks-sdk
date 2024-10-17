package org.jetlinks.sdk.server.device.cmd;

import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.device.manager.BindInfo;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import org.springframework.core.ResolvableType;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

public class BatchBindDeviceCommand  extends AbstractCommand<Mono<Void>, BatchBindDeviceCommand> {

    public static final String TYPE = "type";

    public static final String BIND_INFO = "bindInfo";

    public String getType() {
        return getOrNull(TYPE, String.class);
    }

    public BatchBindDeviceCommand setType(String type) {
        return with(TYPE,type);
    }

    public List<BindInfo> getBindInfo() {
        return ConverterUtils
            .convertToList(
                readable().get(BIND_INFO),
                info -> FastBeanCopier.copy(info, BindInfo.class)
            );
    }

    public BatchBindDeviceCommand setBindInfo(List<BindInfo> bindInfo) {
        return with(BIND_INFO, bindInfo);
    }

    public static CommandHandler<BatchBindDeviceCommand, Mono<Void>> createHandler(Function<BatchBindDeviceCommand, Mono<Void>> handler) {
        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(BatchBindDeviceCommand.class));
                metadata.setName("批量绑定映射");
                metadata.setInputs(CommandMetadataResolver.resolveInputs(ResolvableType.forType(BatchBindDeviceCommand.class)));
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            BatchBindDeviceCommand::new
        );
    }
}
