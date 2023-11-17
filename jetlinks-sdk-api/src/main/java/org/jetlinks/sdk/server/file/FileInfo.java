package org.jetlinks.sdk.server.file;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

@Getter
@Setter
public class FileInfo implements Externalizable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private long length;

    private String md5;

    private String sha256;

    private String accessUrl;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(id);
        out.writeLong(length);
        SerializeUtils.writeNullableUTF(name, out);
        SerializeUtils.writeNullableUTF(accessUrl, out);
        SerializeUtils.writeNullableUTF(md5, out);
        SerializeUtils.writeNullableUTF(sha256, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = in.readUTF();
        length = in.readLong();
        name = SerializeUtils.readNullableUTF(in);
        accessUrl = SerializeUtils.readNullableUTF(in);
        md5 = SerializeUtils.readNullableUTF(in);
        sha256 = SerializeUtils.readNullableUTF(in);
    }
}
