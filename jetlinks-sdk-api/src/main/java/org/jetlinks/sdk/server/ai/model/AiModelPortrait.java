package org.jetlinks.sdk.server.ai.model;

import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.metadata.ConfigMetadata;
import org.jetlinks.core.metadata.DataType;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.*;
import java.util.Map;

/**
 * 模型画像
 */
@Getter
@Setter
public class AiModelPortrait implements Externalizable {

    @Schema(description = "入参及文档")
    private ConfigMetadata input;

    @Schema(description = "出参")
    private DataType output;

    private Map<String, Object> others;


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeByte(1);
        SerializeUtils.writeObject(input, out);
        SerializeUtils.writeObject(output, out);
        SerializeUtils.writeKeyValue(others, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        byte version = in.readByte();
        if (version == 1) {
            input = (ConfigMetadata) SerializeUtils.readObject(in);
            output = (DataType) SerializeUtils.readObject(in);
            others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
        }
    }
}
