package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandUtils;
import org.jetlinks.core.metadata.SimpleFunctionMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author wangsheng
 */
public class UnbindChildDeviceCommand extends AbstractCommand<Mono<Void>, UnbindChildDeviceCommand> {

    public static final String PARENT_ID = "parentId";

    public static final String DEVICE_IDS = "deviceIds";

    public String  getParentId(){
        return getOrNull("parentId", String.class);
    }

    public UnbindChildDeviceCommand setParentId(String parentId){
        return with(PARENT_ID, parentId);
    }

    public List<String> getDeviceIds(){
        return ConverterUtils
            .convertToList(readable().get(DEVICE_IDS), String::valueOf);
    }

    public UnbindChildDeviceCommand setDeviceIds(List<String> deviceIds){
        return with(DEVICE_IDS, deviceIds);
    }

    public static CommandHandler<UnbindChildDeviceCommand, Mono<Void>> createHandler(Function<UnbindChildDeviceCommand, Mono<Void>> handler) {
        return CommandHandler.of(
            () -> {
                SimpleFunctionMetadata metadata = new SimpleFunctionMetadata();
                metadata.setId(CommandUtils.getCommandIdByType(UnbindChildDeviceCommand.class));
                metadata.setName("解绑子设备");
                metadata.setDescription("解绑子设备");
                metadata.setInputs(Collections.singletonList(SimplePropertyMetadata
                                                                 .of("idList", "id数组",
                                                                     new ArrayType().elementType(StringType.GLOBAL))));
                return metadata;
            },
            (cmd, ignore) -> handler.apply(cmd),
            UnbindChildDeviceCommand::new
        );

    }
}