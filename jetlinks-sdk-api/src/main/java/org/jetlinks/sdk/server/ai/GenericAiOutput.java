package org.jetlinks.sdk.server.ai;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.i18n.LocaleUtils;
import org.jetlinks.core.GenericHeaderSupport;
import org.jetlinks.core.utils.SerializeUtils;
import org.jetlinks.sdk.server.ai.cv.AiCommandResult;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * 通用AI输出结果
 *
 * @param <SELF> 自身类型
 * @author zhouhao
 * @since 1.0.1
 */
@Getter
@Setter
public class GenericAiOutput<SELF extends AiCommandResult<SELF>> extends GenericHeaderSupport<SELF> implements AiOutput<SELF> {

    @Schema(title = "源数据id")
    private String sourceId;

    @Schema(title = "数据id")
    private String id;

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
        SerializeUtils.writeNullableUTF(id, out);
        out.writeBoolean(success);
        SerializeUtils.writeNullableUTF(errorMessage, out);
        SerializeUtils.writeNullableUTF(errorCode, out);
        out.writeLong(timestamp);
        SerializeUtils.writeKeyValue(getHeaders(), out);
        SerializeUtils.writeNullableUTF(sourceId, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = SerializeUtils.readNullableUTF(in);
        success = in.readBoolean();
        errorMessage = SerializeUtils.readNullableUTF(in);
        errorCode = SerializeUtils.readNullableUTF(in);
        timestamp = in.readLong();
        SerializeUtils.readKeyValue(in, this::addHeader);
        sourceId = SerializeUtils.readNullableUTF(in);
    }
}