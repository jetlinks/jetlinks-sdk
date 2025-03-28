package org.jetlinks.sdk.server.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.ConverterUtils;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author gyl
 * @since 2.3
 */
@Getter
@Setter
public class SaveByTemplateResult implements Externalizable {
    @Schema(title = "资源id")
    private String id;
    @Schema(description = "实体ID")
    private String targetId;
    @Schema(description = "实体类别")
    private String targetType;
    @Schema(title = "是否成功")
    private boolean success;
    @Schema(title = "数据")
    private Object date;
    @Schema(title = "错误信息")
    private String errorMessage;

    public static SaveByTemplateResult success(EntityTemplateInfo info, Object date) {
        SaveByTemplateResult data = of(info);
        data.setSuccess(true);
        data.setDate(date);
        return data;
    }


    public static SaveByTemplateResult error(EntityTemplateInfo info, String errorMessage) {
        SaveByTemplateResult data = of(info);
        data.setSuccess(false);
        data.setErrorMessage(errorMessage);
        return data;
    }


    public static SaveByTemplateResult of(EntityTemplateInfo info) {
        SaveByTemplateResult data = new SaveByTemplateResult();
        data.setId(info.getId());
        data.setTargetId(info.getTargetId());
        data.setTargetType(info.getTargetType());
        return data;
    }


    public <T> T dateTo(Class<T> clazz) {
        return ConverterUtils.convert(date, clazz);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(id);
        SerializeUtils.writeNullableUTF(targetId, out);
        out.writeUTF(targetType);
        out.writeBoolean(success);
        SerializeUtils.writeNullableUTF(errorMessage, out);
        SerializeUtils.writeObject(SerializeUtils.convertToSafelySerializable(date), out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = in.readUTF();
        targetId = SerializeUtils.readNullableUTF(in);
        targetType = in.readUTF();
        success = in.readBoolean();
        errorMessage = SerializeUtils.readNullableUTF(in);
        date = SerializeUtils.readObject(in);
    }
}
