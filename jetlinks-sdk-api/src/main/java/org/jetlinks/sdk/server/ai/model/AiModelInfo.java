package org.jetlinks.sdk.server.ai.model;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;
import org.jetlinks.sdk.server.ai.AiDomain;
import org.jetlinks.sdk.server.commons.enums.EnumInfo;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

@Getter
@Setter
public class AiModelInfo implements Externalizable {

    private String id;

    private String name;

    private String fileUrl;

    private EnumInfo<String> target;

    private EnumInfo<String> provider;

    private AiDomain domain;

    private Map<String, Object> others;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(id, out);
        SerializeUtils.writeNullableUTF(name, out);
        SerializeUtils.writeNullableUTF(fileUrl, out);

        out.writeBoolean(target != null);
        if (target != null) {
            target.writeExternal(out);
        }

        out.writeBoolean(provider != null);
        if (provider != null) {
            provider.writeExternal(out);
        }
        SerializeUtils.writeKeyValue(others, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = SerializeUtils.readNullableUTF(in);
        name = SerializeUtils.readNullableUTF(in);
        fileUrl = SerializeUtils.readNullableUTF(in);

        if (in.readBoolean()) {
            target = new EnumInfo<>();
            target.readExternal(in);
        }

        if (in.readBoolean()) {
            provider = new EnumInfo<>();
            provider.readExternal(in);
        }
        others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
    }
}
