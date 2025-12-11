package org.jetlinks.sdk.server.ai.cv;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;
import org.jetlinks.sdk.server.file.FileData;
import org.springframework.util.ObjectUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

@Setter
@Getter
public class ImageData implements FileData, Externalizable {

    @Schema(description = "图片源id,例如视频源id")
    private String id;

    @Schema(description = "图片数据")
    private ByteBuf data;

    @Schema(description = "图片类型")
    private Type type;

    @Schema(description = "其他信息")
    private Map<String, Object> others;

    @Override
    public String name() {
        return ObjectUtils.isEmpty(this.others) ?
            "image.jpg" : (String)this.others.getOrDefault("name", "image.jpg");

    }

    @Deprecated
    public ImageData withName(String key, String value) {
        withOther("name", value);
        return this;
    }


    public ImageData withName(String value) {
        withOther("name", value);
        return this;
    }

    public ImageData withOther(String key, Object value) {
        othersWriter().put(key, value);
        return this;
    }

    private synchronized Map<String, Object> othersWriter() {
        return others == null ? others = Maps.newConcurrentMap() : others;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(id, out);
        SerializeUtils.writeKeyValue(others, out);
        SerializeUtils.writeObject(data, out);
        out.writeByte(type.ordinal());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = SerializeUtils.readNullableUTF(in);
        others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
        data = (ByteBuf) SerializeUtils.readObject(in);
        type = Type.ALL[in.readByte()];
    }

    public ByteBuf getData() {
        return body();
    }

    @Override
    public ByteBuf body() {
        return data == null? null : Unpooled.unreleasableBuffer(data);
    }

    @Override
    public void setUrl(String url) {
        withOther("url", url);
    }

    @Override
    public String getUrl() {
        return others == null ? null : (String) others.get("url");
    }

    @Override
    public void release() {
        ReferenceCountUtil.safeRelease(data);
    }

    @Getter
    @AllArgsConstructor
    public enum Type {
        original("原始图像"),
        labeled("边框标记"),
        segmented("语义分割"),
        instance("实例分割"),
        keypoint("关键点标记");


        public static final Type[] ALL = values();

        private final String name;


    }
}
