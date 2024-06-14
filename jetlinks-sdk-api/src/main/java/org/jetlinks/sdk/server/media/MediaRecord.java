package org.jetlinks.sdk.server.media;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MediaRecord implements Externalizable {
    //录像ID
    private String id;

    //设备ID
    private String deviceId;

    //通道ID
    private String channelId;

    //录制开始时间
    @Schema(description = "录像开始时间")
    private Long startTime;

    //录制结束时间
    @Schema(description = "录像结束时间")
    private Long endTime;

    //媒体流开始时间
    @Schema(description = "视频流开始时间")
    private Long streamStartTime;

    //媒体流结束时间
    @Schema(description = "视频流结束时间")
    private Long streamEndTime;

    //流类型
    @Schema(description = "流类型")
    private String streamType;

    //媒体流ID,后续通过此ID停止流
    @Schema(description = "流ID")
    private String streamId;

    //录像文件信息
    private List<MediaRecordFile> files;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(deviceId, out);
        SerializeUtils.writeNullableUTF(channelId, out);
        SerializeUtils.writeObject(startTime, out);
        SerializeUtils.writeObject(endTime, out);
        SerializeUtils.writeObject(streamStartTime, out);
        SerializeUtils.writeObject(streamEndTime, out);
        SerializeUtils.writeNullableUTF(streamType, out);
        SerializeUtils.writeNullableUTF(streamId, out);
        if (files == null) {
            out.writeInt(0);
        } else {
            out.writeInt(files.size());
            for (MediaRecordFile file : files) {
                file.writeExternal(out);
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        deviceId = SerializeUtils.readNullableUTF(in);
        channelId = SerializeUtils.readNullableUTF(in);
        startTime = (Long) SerializeUtils.readObject(in);
        endTime = (Long) SerializeUtils.readObject(in);
        streamStartTime = (Long) SerializeUtils.readObject(in);
        streamEndTime = (Long) SerializeUtils.readObject(in);
        streamType = SerializeUtils.readNullableUTF(in);
        streamId = SerializeUtils.readNullableUTF(in);
        int size = in.readInt();
        if (size > 0) {
            files = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                MediaRecordFile file = new MediaRecordFile();
                file.readExternal(in);
                files.add(file);
            }
        }
    }
}
