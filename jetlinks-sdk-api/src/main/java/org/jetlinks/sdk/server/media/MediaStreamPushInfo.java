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
public class MediaStreamPushInfo implements Externalizable {

    /**
     * 流媒体服务ID
     */
    @Schema(title = "流媒体服务ID")
    private String serverId;

    /**
     * <pre>{@code
     *  rtsp://127.0.0.1:554
     * }</pre>
     */
    @Schema(title = "RTSP推流地址")
    private String rtsp;

    /**
     * <pre>{@code
     *  rtmp://127.0.0.1:1935
     * }</pre>
     */
    @Schema(title = "RTMP推流地址")
    private String rtmp;

    /**
     * <pre>{@code
     *  rtp://127.0.0.1:10000
     * }</pre>
     */
    @Schema(title = "RTP推流地址")
    private String rtp;

    /**
     * rtp推流时的SSRC值
     */
    @Schema(title = "rtp推流时的SSRC值")
    private String ssrc;

    /**
     * 播放信息,当视频流推送到流媒体服务器后,可以通过此信息获取播放地址.
     */
    @Schema(title = "播放信息")
    private MediaInfo playInfo;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(serverId, out);
        SerializeUtils.writeNullableUTF(rtsp, out);
        SerializeUtils.writeNullableUTF(rtmp, out);
        SerializeUtils.writeNullableUTF(rtp, out);
        SerializeUtils.writeNullableUTF(ssrc, out);
        out.writeBoolean(playInfo == null);
        if (playInfo != null) {
            playInfo.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        serverId = SerializeUtils.readNullableUTF(in);
        rtsp = SerializeUtils.readNullableUTF(in);
        rtmp = SerializeUtils.readNullableUTF(in);
        rtp = SerializeUtils.readNullableUTF(in);
        ssrc = SerializeUtils.readNullableUTF(in);
        if (!in.readBoolean()) {
            playInfo = new MediaInfo();
            playInfo.readExternal(in);
        }
    }
}
