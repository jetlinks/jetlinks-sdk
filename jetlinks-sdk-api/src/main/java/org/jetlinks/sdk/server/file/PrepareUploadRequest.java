package org.jetlinks.sdk.server.file;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gyl
 * @since 2.3
 */
@Getter
@Setter
public class PrepareUploadRequest implements Externalizable {
    private static final long serialVersionUID = 1L;
    private String name;
    private long length;
    private String md5;
    private String sha256;
    private long createTime;

    public Map<String, Object> toMap() {
        return FastBeanCopier.copy(this, HashMap::new);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeLong(length);
        SerializeUtils.writeNullableUTF(md5, out);
        SerializeUtils.writeNullableUTF(sha256, out);
        out.writeLong(createTime);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        length = in.readLong();
        md5 = SerializeUtils.readNullableUTF(in);
        sha256 = SerializeUtils.readNullableUTF(in);
        createTime = in.readLong();

    }
}
