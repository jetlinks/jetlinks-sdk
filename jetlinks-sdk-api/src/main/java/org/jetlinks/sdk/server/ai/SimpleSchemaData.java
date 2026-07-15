package org.jetlinks.sdk.server.ai;

import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Map;

public class SimpleSchemaData extends HashMap<String, Object> implements Externalizable {

    public SimpleSchemaData(){

    }

    public SimpleSchemaData(Map<String,Object> data){
        super(data);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeKeyValue(this, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        SerializeUtils.readKeyValue(in, this::put);
    }
}
