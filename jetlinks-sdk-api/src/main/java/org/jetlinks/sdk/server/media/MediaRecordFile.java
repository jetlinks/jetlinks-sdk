package org.jetlinks.sdk.server.media;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

@Getter
@Setter
public class MediaRecordFile extends MediaInfo {

    /**
     * 文件路径
     */
    private String path;

    /**
     * 录像文件时间点
     */
    private long time;

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        path = SerializeUtils.readNullableUTF(in);
        time = in.readLong();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        SerializeUtils.writeNullableUTF(path, out);
        out.writeLong(time);
    }
}
