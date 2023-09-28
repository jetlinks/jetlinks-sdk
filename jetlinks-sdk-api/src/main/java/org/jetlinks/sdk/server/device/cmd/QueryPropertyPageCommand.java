package org.jetlinks.sdk.server.device.cmd;

import org.jetlinks.core.metadata.PropertyMetadata;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.commons.cmd.QueryListCommand;
import org.jetlinks.sdk.server.commons.cmd.QueryPagerCommand;
import org.jetlinks.sdk.server.device.DeviceProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询设备属性命令
 *
 * <pre>{@code
 *
 * {
 *     "property":"属性ID",
 *     "deviceId":"设备ID,和产品ID不能同时为空",
 *     "productId":"产品ID,和设备ID不能同时为空",
 *     "pageSize":10,
 *     "pageIndex":0,
 *     "terms":[
 *          {
 *              "column":"numberValue",
 *              "termType":"gt",
 *              "value":"10"
 *          }
 *      ]
 * }
 *
 * }</pre>
 *
 * @author zhouhao
 * @since 1.0
 */
public class QueryPropertyPageCommand extends QueryPagerCommand<DeviceProperty> {

    public String getProperty() {
        return (String) readable().get("property");
    }

    public QueryPropertyPageCommand withProperty(String property) {
        writable().put("property", property);
        return this;
    }

    public String getDeviceId() {
        return (String) readable().get("deviceId");
    }

    public QueryPropertyPageCommand withDeviceId(String deviceId) {
        writable().put("deviceId", deviceId);
        return this;
    }

    public String getProductId() {
        return (String) readable().get("productId");
    }

    public QueryPropertyPageCommand withProductId(String productId) {
        writable().put("productId", productId);
        return this;
    }

    @SuppressWarnings("all")
    public static List<PropertyMetadata> getQueryParamMetadata() {
        List<PropertyMetadata> list = new ArrayList<>(QueryPagerCommand.getQueryParamMetadata());
        list.add(SimplePropertyMetadata.of("property", "属性ID", StringType.GLOBAL));
        list.add(SimplePropertyMetadata.of("deviceId", "设备ID", StringType.GLOBAL));
        list.add(SimplePropertyMetadata.of("productId", "产品ID", StringType.GLOBAL));
        return list;
    }

}
