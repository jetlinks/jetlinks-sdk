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
public class SaveByTemplateData implements Externalizable {
    @Schema(title = "id")
    private String id;
    @Schema(title = "是否成功")
    private boolean success;
    @Schema(title = "数据")
    private Object date;
    @Schema(title = "错误信息")
    private String errorMessage;


    public static SaveByTemplateData success(String id, Object date) {
        SaveByTemplateData data = new SaveByTemplateData();
        data.setSuccess(true);
        data.setId(id);
        data.setDate(date);
        return data;
    }


    public static SaveByTemplateData error(String id, String errorMessage) {
        SaveByTemplateData data = new SaveByTemplateData();
        data.setSuccess(false);
        data.setId(id);
        data.setErrorMessage(errorMessage);
        return data;
    }


    public <T> T dateTo(Class<T> clazz) {
        return ConverterUtils.convert(date, clazz);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(id);
        out.writeBoolean(success);
        SerializeUtils.writeNullableUTF(errorMessage, out);
        SerializeUtils.writeObject(SerializeUtils.convertToSafelySerializable(date), out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = in.readUTF();
        success = in.readBoolean();
        errorMessage = SerializeUtils.readNullableUTF(in);
        date = SerializeUtils.readObject(in);
    }
}
