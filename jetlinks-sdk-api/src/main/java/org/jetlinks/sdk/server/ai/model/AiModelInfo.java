package org.jetlinks.sdk.server.ai.model;

import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "模型id")
    private String id;

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "模型名称")
    private String fileUrl;

    @Schema(description = "文件md5值")
    private String md5;

    private EnumInfo<String> target;

    private EnumInfo<String> provider;

    private AiDomain domain;

    private Map<String, Object> others;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(id, out);
        SerializeUtils.writeNullableUTF(name, out);
        SerializeUtils.writeNullableUTF(fileUrl, out);
        SerializeUtils.writeNullableUTF(md5, out);

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
        md5 = SerializeUtils.readNullableUTF(in);

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
