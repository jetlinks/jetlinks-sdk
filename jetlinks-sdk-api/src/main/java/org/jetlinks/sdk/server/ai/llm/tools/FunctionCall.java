package org.jetlinks.sdk.server.ai.llm.tools;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

@Getter
@Setter
public class FunctionCall implements Externalizable {

    private String name;

    private Map<String, Object> arguments;

    @JsonIgnore
    private Map<String, Object> others;

    @JsonAnyGetter
    public Map<String, Object> getOthers() {
        return others;
    }

    @JsonAnySetter
    public void withOther(String key, Object value) {
        if (others == null) {
            others = new java.util.HashMap<>();
        }
        others.put(key, value);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(name, out);
        SerializeUtils.writeKeyValue(arguments, out);
        SerializeUtils.writeKeyValue(others, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = SerializeUtils.readNullableUTF(in);
        arguments = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
        others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
    }
}
