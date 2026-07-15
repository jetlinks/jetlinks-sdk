package org.jetlinks.sdk.server.ai;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jetlinks.core.utils.SerializeUtils;
import org.jetlinks.sdk.server.file.FileData;

import javax.annotation.Nullable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

@Getter
@Setter
public class SimpleFileData implements FileData, Externalizable {

    @Schema(description = "文件名称，需携带后缀")
    private String name;

    @Schema(description = "文件数据")
    @Nullable
    private ByteBuf data;

    @Schema(description = "文件url")
    private String url;

    @Schema(description = "其他信息")
    private Map<String, Object> others;

    @Override
    public String name() {
       return name;
    }

    @Override
    public void setInternalUrl(String internalUrl) {
        withOther("internalUrl", internalUrl);
    }

    @JsonIgnore
    @Override
    public String getInternalUrl() {
        if (others != null) {
            String internalUrl = (String) others.get("internalUrl");
            if (StringUtils.isNotBlank(internalUrl)) {
                return internalUrl;
            }
        }
        return FileData.super.getInternalUrl();
    }

    public SimpleFileData withOther(String key, Object value) {
        if(others == null) {
            others = Maps.newHashMap();
        }
        others.put(key, value);
        return this;
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(name, out);
        SerializeUtils.writeObject(data, out);
        SerializeUtils.writeNullableUTF(url, out);
        SerializeUtils.writeKeyValue(others, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = SerializeUtils.readNullableUTF(in);
        data = (ByteBuf) SerializeUtils.readObject(in);
        url = SerializeUtils.readNullableUTF(in);
        others = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
    }

    public ByteBuf getData() {
        return body();
    }

    @Override
    public ByteBuf body() {
        return data == null ? null : Unpooled.unreleasableBuffer(data);
    }

    @Override
    public void release() {
        ReferenceCountUtil.safeRelease(data);
    }
}
