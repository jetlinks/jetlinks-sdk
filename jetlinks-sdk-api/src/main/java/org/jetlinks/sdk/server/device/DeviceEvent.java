package org.jetlinks.sdk.server.device;

import java.util.HashMap;
import java.util.Map;

public class DeviceEvent extends HashMap<String, Object>{

    public DeviceEvent(){

    }

    public DeviceEvent(Map<String,Object> data){
        super(data);
    }
}
