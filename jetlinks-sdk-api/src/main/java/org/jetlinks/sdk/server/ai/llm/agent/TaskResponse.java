package org.jetlinks.sdk.server.ai.llm.agent;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.NoArgsConstructor;
import org.hswebframework.ezorm.core.Extendable;
import org.jetlinks.core.GenericHeaderSupport;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public class TaskResponse extends GenericHeaderSupport<TaskResponse> implements Extendable, Externalizable {

    private volatile Map<String, Object> extensions;

    public TaskResponse(Map<String, Object> extensions) {
        this.extensions = new ConcurrentHashMap<>(extensions);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeKeyValue(getHeaders(), out);
        SerializeUtils.writeKeyValue(extensions, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        SerializeUtils.readKeyValue(in, this::addHeader);
        SerializeUtils.readKeyValue(in,this::setExtension);
    }


    @Override
    @JsonAnyGetter
    public Map<String, Object> extensions() {
        return extensions;
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
}
