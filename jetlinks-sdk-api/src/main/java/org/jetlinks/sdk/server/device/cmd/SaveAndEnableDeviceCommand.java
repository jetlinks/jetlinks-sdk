package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.sdk.server.commons.cmd.SaveAndEnableCommand;
import org.jetlinks.sdk.server.device.DeviceInfo;

/**
 * @author wangsheng
 */
@Schema(description = "创建并启用设备命令")
public class SaveAndEnableDeviceCommand extends SaveAndEnableCommand<DeviceInfo> {

}