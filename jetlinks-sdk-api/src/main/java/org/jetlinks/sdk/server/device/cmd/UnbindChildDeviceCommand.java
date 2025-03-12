package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.annotation.ui.Selector;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.command.CommandHandler;
import org.jetlinks.core.command.CommandMetadataResolver;
import org.jetlinks.core.metadata.FunctionMetadata;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

/**
 * @author wangsheng
 */
@Schema(title = "解除子设备绑定")
public class UnbindChildDeviceCommand extends AbstractCommand<Mono<Void>, UnbindChildDeviceCommand> {

    public static final String PARENT_ID = "parentId";

    public static final String DEVICE_IDS = "deviceIds";

    @Selector(type = "device")
    @Schema(description = "父设备ID")
    public String  getParentId(){
        return getOrNull("parentId", String.class);
    }

    public UnbindChildDeviceCommand setParentId(String parentId){
        return with(PARENT_ID, parentId);
    }

    @Selector(multiple = true, type = "device")
    @Schema(description = "设备ID集合")
    public List<String> getDeviceIds(){
        return ConverterUtils
            .convertToList(readable().get(DEVICE_IDS), String::valueOf);
    }

    public UnbindChildDeviceCommand setDeviceIds(List<String> deviceIds){
        return with(DEVICE_IDS, deviceIds);
    }

    public static CommandHandler<UnbindChildDeviceCommand, Mono<Void>> createHandler(Function<UnbindChildDeviceCommand, Mono<Void>> handler) {
        return CommandHandler.of(
            UnbindChildDeviceCommand::metadata,
            (cmd, ignore) -> handler.apply(cmd),
            UnbindChildDeviceCommand::new
        );

    }

    public static FunctionMetadata metadata() {
        return CommandMetadataResolver.resolve(UnbindChildDeviceCommand.class);
    }
}