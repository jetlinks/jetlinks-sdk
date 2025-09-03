package org.jetlinks.sdk.server.ai.llm.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;
import org.jetlinks.sdk.server.ai.cv.AiCommandResult;
import org.jetlinks.sdk.server.ai.cv.ImageData;
import org.jetlinks.sdk.server.ai.cv.UploadFileInfo;
import org.jetlinks.sdk.server.file.FileData;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class ChatResponse extends AiCommandResult<ChatResponse> implements Externalizable {

    @Schema(title = "响应类型")
    private Type type;

    @Schema(title = "响应文本内容", description = "对话文本消息内容")
    private String content;

    @Schema(title = "响应图片内容", description = "对话产生的图片内容")
    private List<ImageData> images;

    @Schema(title = "响应文件内容", description = "对话产生的文件内容")
    private List<UploadFileInfo> files;

    public ChatResponse() {
    }

    @Override
    public List<? extends FileData> files() {
        List<FileData> fileData = new ArrayList<>();
        if (images != null) {
            fileData.addAll(images);
        }
        if (files != null) {
            fileData.addAll(files);
        }
        return fileData;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        // version
        out.writeByte(0x01);

        // type
        out.writeByte(type.ordinal());

        // headers
        SerializeUtils.writeKeyValue(getHeaders(), out);

        // content
        SerializeUtils.writeNullableUTF(content, out);

        // images
        if (images == null) {
            out.writeInt(0);
        } else {
            out.writeInt(images.size());
            for (ImageData image : images) {
                image.writeExternal(out);
            }
        }

        // files
        if (files == null) {
            out.writeInt(0);
        } else {
            out.writeInt(files.size());
            for (UploadFileInfo fileData : files) {
                fileData.writeExternal(out);
            }
        }

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        // version
        int ignore = in.readByte();

        // type
        type = Type.ALL[in.readByte()];

        // headers
        setHeaders(SerializeUtils.readMap(in, ConcurrentHashMap::new));

        // content
        content = SerializeUtils.readNullableUTF(in);

        // images
        {
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

        // files
        {
            int size = in.readInt();
            if (size > 0) {
                files = new java.util.ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    UploadFileInfo image = new UploadFileInfo();
                    image.readExternal(in);
                    files.add(image);
                }
            }
        }


    }

    public enum Type {

        @Schema(title = "对话完成")
        complete,

        @Schema(title = "流式对话响应")
        streaming,
        ;

        static final Type[] ALL = Type.values();

    }

}
