package org.jetlinks.sdk.server.device.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.sdk.server.commons.cmd.QueryPagerCommand;
import org.jetlinks.sdk.server.device.DeviceDetail;

@Schema(title = "分页查询设备详情(含产品信息)")
public class QueryDeviceDetailPagerCommand extends QueryPagerCommand<DeviceDetail> {

}