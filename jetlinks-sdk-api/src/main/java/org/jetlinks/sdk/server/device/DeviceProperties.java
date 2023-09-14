package org.jetlinks.sdk.server.device;

import java.util.HashMap;
import java.util.Map;

public class DeviceProperties extends HashMap<String, Object>{

    public DeviceProperties(){

    }

    public DeviceProperties(Map<String,Object> data){
        super(data);
    }
}
