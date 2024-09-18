package org.jetlinks.sdk.server.ai.cv;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.GenericHeaderSupport;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author gyl
 * @since 1.0.1
 */
@Getter
@Setter
public class AiCommandResult<SELF extends AiCommandResult<SELF>> extends GenericHeaderSupport<SELF> implements Externalizable {

    @Schema(title = "是否成功响应")
    private boolean success;

    @Schema(title = "错误信息")
    private String errorMessage;

    @Schema(title = "错误码")
    private String errorCode;

    @Schema(title = "时间戳")
    private long timestamp = System.currentTimeMillis();

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBoolean(success);
        SerializeUtils.writeNullableUTF(errorMessage, out);
        SerializeUtils.writeNullableUTF(errorCode, out);
        out.writeLong(timestamp);
        SerializeUtils.writeKeyValue(getHeaders(), out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        success = in.readBoolean();
        errorMessage = SerializeUtils.readNullableUTF(in);
        errorCode = SerializeUtils.readNullableUTF(in);
        timestamp = in.readLong();
        SerializeUtils.readKeyValue(in, this::addHeader);
    }
}
