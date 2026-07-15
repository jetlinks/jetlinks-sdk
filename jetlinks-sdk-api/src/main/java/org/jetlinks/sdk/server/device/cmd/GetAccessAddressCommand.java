package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.jetlinks.sdk.server.commons.cmd.OperationByIdCommand;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 获取设备接入网关的接入地址信息.
 *
 * @author zhouhao
 * @since 1.1.1
 */
@Schema(title = "获取设备接入网关的接入地址信息")
public class GetAccessAddressCommand extends OperationByIdCommand<Flux<String>, GetAccessAddressCommand> {

    private static final long serialVersionUID = 1L;

    @Override
    @Schema(name = PARAMETER_KEY_ID, title = "设备接入网关ID")
    @NotBlank
    public String getId() {
        return super.getId();
    }

    @Schema(hidden = true)
    @Override
    public List<Object> getIdList() {
        return super.getIdList();
    }
}
