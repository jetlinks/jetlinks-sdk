package org.jetlinks.sdk.server.file;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashSet;
import java.util.Set;

/**
 * @author gyl
 * @since 2.3
 */
@Getter
@Setter
public class DeleteFileRequest implements Externalizable {
    private static final long serialVersionUID = 1L;
    /**
     * 仅删除临时文件
     */
    private boolean onlyDelTempFile = false;

    private Type type;
    /**
     * 删除指定id的文件,type=[ids]时不可为空
     */
    private Set<String> ids;
    /**
     * 删除创建时间小于等于指定时间的文件,type=[createTime]时不可为空
     */
    private long beforeCreateTime;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBoolean(onlyDelTempFile);
        out.writeUTF(type.name());
        if (CollectionUtils.isEmpty(ids)) {
            out.writeInt(0);
        } else {
            out.writeInt(ids.size());
            for (String id : ids) {
                out.writeUTF(id);
            }
        }
        out.writeLong(beforeCreateTime);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        onlyDelTempFile = in.readBoolean();
        type = Type.valueOf(in.readUTF());
        int size = in.readInt();
        if (size > 0) {
            ids = new HashSet<>(size);
            for (int i = 0; i < size; i++) {
                ids.add(in.readUTF());
            }
        }
        beforeCreateTime = in.readLong();
    }

    private enum Type {
        ids,
        createTime
    }
}
