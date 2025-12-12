package org.jetlinks.sdk.server.ai.llm;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.core.Extendable;
import org.jetlinks.core.GenericHeaderSupport;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LlmMessage<SELF extends LlmMessage<SELF>> extends GenericHeaderSupport<SELF> implements Extendable , Externalizable {
    @Getter
    @Setter
    private String type;

    private volatile Map<String, Object> extensions;

    @Override
    @JsonAnyGetter
    public Map<String, Object> extensions() {
        return extensions == null ? Collections.emptyMap() : extensions;
    }

    @Override
    @JsonAnySetter
    public void setExtension(String property, Object value) {
        if (extensions == null) {
            synchronized (this) {
                if (extensions == null) {
                    extensions = new ConcurrentHashMap<>();
                }
            }
        }
        extensions.put(property, value);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(type,out);
        SerializeUtils.writeKeyValue(getHeaders(),out);
        SerializeUtils.writeKeyValue(extensions,out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        type = SerializeUtils.readNullableUTF(in);
        SerializeUtils.readKeyValue(in,this::addHeader);
        SerializeUtils.readKeyValue(in,this::setExtension);

    }
}
