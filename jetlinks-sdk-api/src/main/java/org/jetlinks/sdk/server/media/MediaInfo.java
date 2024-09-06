package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MediaInfo implements Externalizable {
    @Schema(description = "流ID")
    private String streamId;

    @Schema(description = "rtsp地址")
    private String rtsp;

    @Schema(description = "rtmp地址")
    private String rtmp;

    @Schema(description = "flv地址")
    private String flv;

    @Schema(description = "mp4地址")
    private String mp4;

    @Schema(description = "rtc地址")
    private String rtc;


    public List<URI> toUriList() {
        List<URI> list = new ArrayList<>(5);
        if (rtsp != null) {
            list.add(URI.create(rtsp));
        }
        if (rtmp != null) {
            list.add(URI.create(rtmp));
        }
        if (flv != null) {
            list.add(URI.create(flv));
        }
        if (mp4 != null) {
            list.add(URI.create(mp4));
        }
        if (rtc != null) {
            list.add(URI.create(rtc));
        }
        return list;

    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(streamId, out);
        SerializeUtils.writeNullableUTF(rtsp, out);
        SerializeUtils.writeNullableUTF(rtmp, out);
        SerializeUtils.writeNullableUTF(flv, out);
        SerializeUtils.writeNullableUTF(mp4, out);
        SerializeUtils.writeNullableUTF(rtc, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        streamId = SerializeUtils.readNullableUTF(in);
        rtsp = SerializeUtils.readNullableUTF(in);
        rtmp = SerializeUtils.readNullableUTF(in);
        flv = SerializeUtils.readNullableUTF(in);
        mp4 = SerializeUtils.readNullableUTF(in);
        rtc = SerializeUtils.readNullableUTF(in);
    }
}
