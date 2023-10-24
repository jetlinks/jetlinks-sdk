package org.jetlinks.sdk.server.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hswebframework.web.dict.EnumDict;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class EnumInfo<T> implements Externalizable {

    private static final long serialVersionUID = 1;

    private T value;
    private String text;

    public static <T> EnumInfo<T> of(EnumDict<T> dic) {
        return of(dic.getValue(), dic.getText());
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeObject(value, out);
        out.writeUTF(text);
    }

    @Override
    @SuppressWarnings("all")
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        value = (T) SerializeUtils.readObject(in);
        text = in.readUTF();
    }
}
