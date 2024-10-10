package org.jetlinks.sdk.server.template;

import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.metadata.PropertyMetadata;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.utils.MetadataUtils;
import org.jetlinks.core.utils.SerializeUtils;
import org.jetlinks.sdk.server.utils.ObjectMappers;
import org.springframework.core.ResolvableType;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
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


    public static EntityTemplateInfo of(Object object) {
        if (object instanceof EntityTemplateInfo) {
            return ((EntityTemplateInfo) object);
        }
        return FastBeanCopier.copy(object, new EntityTemplateInfo());
    }

    public <T> T metadataTo(Class<T> clazz) {
        return ObjectMappers.parseJson(metadata, clazz);
    }

    public static List<PropertyMetadata> parseMetadata() {
        ObjectType objectType = (ObjectType) MetadataUtils.parseType(ResolvableType.forClass(EntityTemplateInfo.class));
        return objectType.getProperties();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(category, out);
        SerializeUtils.writeNullableUTF(targetId, out);
        out.writeUTF(targetType);
        out.writeUTF(metadata);
        SerializeUtils.writeKeyValue(properties, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        category = SerializeUtils.readNullableUTF(in);
        targetId = SerializeUtils.readNullableUTF(in);
        targetType = in.readUTF();
        metadata = in.readUTF();
        properties = SerializeUtils.readMap(in, Maps::newHashMapWithExpectedSize);
    }
}
