package org.jetlinks.sdk.server.ai.llm.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jetlinks.core.utils.SerializeUtils;
import org.jetlinks.sdk.server.ai.cv.UploadFileInfo;
import org.jetlinks.sdk.server.ai.cv.ImageData;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

@Data
public class ChatMessage implements Externalizable {

    @Schema(title = "消息内容",description = "对话文本消息内容")
    private String content;

    @Schema(title = "消息图片",description = "对话图片消息内容")
    private List<ImageData> images;

    @Schema(title = "上传文件信息列表", description = "对话上传文件内容")
    public List<UploadFileInfo> files;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        //ver
        out.writeByte(0x01);

        SerializeUtils.writeNullableUTF(content, out);
        if (images == null) {
            out.writeInt(0);
        } else {
            int size = images.size();
            out.writeInt(size);
            for (ImageData image : images) {
                image.writeExternal(out);
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        //ver
        int ignore = in.readByte();

        content = SerializeUtils.readNullableUTF(in);
        int size = in.readInt();
        if (size > 0) {
            images = new java.util.ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                ImageData image = new ImageData();
                image.readExternal(in);
                images.add(image);
            }
        }
    }
}
