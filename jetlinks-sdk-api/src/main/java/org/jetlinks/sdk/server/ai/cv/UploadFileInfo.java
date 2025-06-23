package org.jetlinks.sdk.server.ai.cv;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;
import org.jetlinks.sdk.server.file.FileData;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 *
 * @author zhangji 2025/6/3
 * @since 2.10
 */
@Getter
@Setter
public class UploadFileInfo implements FileData, Externalizable {

    /**
     * @see MediaType#toString()
     */
    @Schema(description = "文件类型")
    private String mediaType;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "文件地址，传递方式为文件地址时使用")
    private String url;

    @Schema(description = "文件ID，传递方式为上传文件时使用")
    private String fileId;

    @Schema(description = "文件数据，传递方式为直接传输时使用")
    private ByteBuf data;

    @Schema(description = "文件后缀")
    private String extension;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(mediaType, out);
        SerializeUtils.writeNullableUTF(fileName, out);
        SerializeUtils.writeNullableUTF(url, out);
        SerializeUtils.writeNullableUTF(fileId, out);
        SerializeUtils.writeObject(data, out);
        SerializeUtils.writeNullableUTF(extension, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        mediaType = SerializeUtils.readNullableUTF(in);
        fileName = SerializeUtils.readNullableUTF(in);
        url = SerializeUtils.readNullableUTF(in);
        fileId = SerializeUtils.readNullableUTF(in);
        data = (ByteBuf) SerializeUtils.readObject(in);
        extension = SerializeUtils.readNullableUTF(in);
    }

    @Override
    public String name() {
        if (StringUtils.hasText(fileName)) {
            return fileName;
        }
        if (StringUtils.hasText(url)) {
            return url;
        }
        if (StringUtils.hasText(fileId)) {
            return fileId;
        }
        return "";
    }

    @Override
    public ByteBuf body() {
        return data == null? null : Unpooled.unreleasableBuffer(data);
    }

    @Override
    public void release() {
        ReferenceCountUtil.safeRelease(data);
    }

}
