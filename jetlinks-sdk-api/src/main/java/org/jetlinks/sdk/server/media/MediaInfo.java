package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

@Getter
@Setter
public class MediaInfo implements Externalizable {
    @Schema(description = "流ID")
    private String streamId;

    @Schema(description = "rtsp播放地址")
    private String rtsp;

    @Schema(description = "rtmp播放地址")
    private String rtmp;

    @Schema(description = "flv播放地址")
    private String flv;

    @Schema(description = "mp4播放地址")
    private String mp4;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(streamId,out);
        SerializeUtils.writeNullableUTF(rtsp,out);
        SerializeUtils.writeNullableUTF(rtmp,out);
        SerializeUtils.writeNullableUTF(flv,out);
        SerializeUtils.writeNullableUTF(mp4,out);

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        streamId = SerializeUtils.readNullableUTF(in);
        rtsp = SerializeUtils.readNullableUTF(in);
        rtmp = SerializeUtils.readNullableUTF(in);
        flv = SerializeUtils.readNullableUTF(in);
        mp4 = SerializeUtils.readNullableUTF(in);
    }
}
