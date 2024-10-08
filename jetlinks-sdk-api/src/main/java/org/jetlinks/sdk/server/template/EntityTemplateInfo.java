package org.jetlinks.sdk.server.template;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

/**
 * @author gyl
 * @since 1.0.1
 */
@Getter
@Setter
@Schema(title = "资源模板信息")
public class EntityTemplateInfo implements Externalizable {

    @Schema(description = "模板分类")
    private String category;

    @Schema(description = "实体ID")
    private String targetId;

    @Schema(description = "实体类别")
    private String targetType;

    @Schema(description = "模板内容")
    private String metadata;

    @Schema(description = "额外信息")
    private Map<String, Object> properties;

    @Schema(description = "文件数据")
    private ByteBuf file;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(category, out);
        out.writeUTF(targetId);
        out.writeUTF(targetType);
        out.writeUTF(metadata);
        SerializeUtils.writeKeyValue(properties, out);
        SerializeUtils.writeObject(file, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        category = SerializeUtils.readNullableUTF(in);
        targetId = in.readUTF();
        targetType = in.readUTF();
        metadata = in.readUTF();
        properties = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
        file = (ByteBuf) SerializeUtils.readObject(in);
    }
}
