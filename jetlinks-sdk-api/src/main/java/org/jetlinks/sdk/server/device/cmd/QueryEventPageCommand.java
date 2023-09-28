package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.metadata.PropertyMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.commons.cmd.QueryListCommand;
import org.jetlinks.sdk.server.commons.cmd.QueryPagerCommand;
import org.jetlinks.sdk.server.device.DeviceEvent;
import org.jetlinks.sdk.server.device.DeviceProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询设备事件命令
 *
 * <pre>{@code
 *
 * {
 *     "eventId":"事件ID",
 *     "deviceId":"设备ID,和产品ID不能同时为空",
 *     "productId":"产品ID,和设备ID不能同时为空",
 *     "pageSize":25,
 *     "pageIndex":0,
 *     "terms":[
 *          {
 *              "column":"type",
 *              "termType":"is",
 *              "value":"warn"
 *          }
 *      ]
 * }
 *
 * }</pre>
 *
 * @author zhouhao
 * @since 1.0
 */
public class QueryEventPageCommand extends QueryPagerCommand<DeviceEvent> {

    public String getEventId() {
        return (String) readable().get("eventId");
    }

    public QueryEventPageCommand withEventId(String eventId) {
        writable().put("eventId", eventId);
        return this;
    }

    public String getDeviceId() {
        return (String) readable().get("deviceId");
    }

    public QueryEventPageCommand withDeviceId(String deviceId) {
        writable().put("deviceId", deviceId);
        return this;
    }

    public String getProductId() {
        return (String) readable().get("productId");
    }

    public QueryEventPageCommand withProductId(String productId) {
        writable().put("productId", productId);
        return this;
    }

    @SuppressWarnings("all")
    public static List<PropertyMetadata> getQueryParamMetadata() {
        List<PropertyMetadata> list = new ArrayList<>(QueryPagerCommand.getQueryParamMetadata());
        list.add(SimplePropertyMetadata.of("eventId", "事件ID", StringType.GLOBAL));
        list.add(SimplePropertyMetadata.of("deviceId", "设备ID", StringType.GLOBAL));
        list.add(SimplePropertyMetadata.of("productId", "产品ID", StringType.GLOBAL));
        return list;
    }


}
