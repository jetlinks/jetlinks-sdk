package org.jetlinks.sdk.server.device;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.things.ThingProperty;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

@Getter
@Setter
public class CachedDeviceProperty implements Externalizable {

    private String property;

    private Object value;

    private long timestamp;

    private String state;

    public static CachedDeviceProperty of(ThingProperty property){
        CachedDeviceProperty prop = new CachedDeviceProperty();
        prop.timestamp = property.getTimestamp();
        prop.property = property.getProperty();
        prop.value = property.getValue();
        prop.state = property.getState();
        return prop;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(property, out);
        SerializeUtils.writeObject(value, out);
        out.writeLong(timestamp);
        SerializeUtils.writeNullableUTF(state, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        property = SerializeUtils.readNullableUTF(in);
        value = SerializeUtils.readObject(in);
        timestamp = in.readLong();
        state = SerializeUtils.readNullableUTF(in);
    }
}
