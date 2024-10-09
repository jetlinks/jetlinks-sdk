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
    @Schema(title = "数据")
    private Object date;


    public <T> T dateTo(Class<T> clazz) {
        return ConverterUtils.convert(date, clazz);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(id);
        SerializeUtils.writeObject(SerializeUtils.convertToSafelySerializable(date), out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = SerializeUtils.readNullableUTF(in);
        date = SerializeUtils.readObject(in);
    }
}
