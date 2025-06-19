package org.jetlinks.sdk.server.device;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.jetlinks.core.message.DeviceMessage;
import org.jetlinks.core.message.property.PropertyMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class DevicePropertyProperties extends HashMap<String, Object> {


    public String getDeviceId() {
        return (String) get("deviceId");
    }

    public void setDeviceId(String deviceId) {
        put("deviceId", deviceId);
    }


    public DevicePropertyProperties with(DeviceMessage message, Predicate<String> propertyFilter) {
        setDeviceId(message.getDeviceId());
        if (message instanceof PropertyMessage) {
            Map<String, Object> prop = ((PropertyMessage) message).getProperties();
            if (MapUtils.isNotEmpty(prop)) {
                putAll(Maps.filterKeys(prop, propertyFilter::test));
            }
        }

        return this;
    }

}
