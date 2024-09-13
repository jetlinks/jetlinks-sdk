package org.jetlinks.sdk.server.ai.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * 模型操作结果
 */
@Getter
@Setter
public class AiModelOperationResults implements Externalizable {

    @Schema(description = "模型id")
    private String id;

    @Schema(description = "是否成功")
    private boolean success;

    @Schema(description = "失败原因")
    private String failReason;

    @Schema(description = "解析结果")
    private AiModelPortrait parsed;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeByte(1);
        SerializeUtils.writeNullableUTF(id, out);
        out.writeBoolean(success);
        SerializeUtils.writeNullableUTF(failReason, out);
        parsed.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        byte version = in.readByte();
        if (version == 1) {
            id = SerializeUtils.readNullableUTF(in);
            success = in.readBoolean();
            failReason = SerializeUtils.readNullableUTF(in);
            parsed = new AiModelPortrait();
            parsed.readExternal(in);
        }
    }
}
