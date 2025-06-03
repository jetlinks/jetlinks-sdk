package org.jetlinks.sdk.server.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class DimensionInfo implements Externalizable {

    @Schema(title = "维度类型", description = "维度类型,如:org,role.")
    private String type;

    @Schema(title = "维度ID", description = "维度ID,如:组织ID,角色ID.")
    private String id;

    @Schema(title = "其他配置信息")
    private Map<String, Object> options;

    public DimensionInfo() {
    }

    public DimensionInfo(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public DimensionInfo(String type, String id, Map<String, Object> options) {
        this.type = type;
        this.id = id;
        this.options = options;
    }

    public DimensionInfo of(String type, String id) {
        return new DimensionInfo(type, id);
    }

    public DimensionInfo of(String type, String id, Map<String, Object> options) {
        return new DimensionInfo(type, id, options);
    }

    public void withOption(String key, Object value) {
        if (options == null) {
            options = new HashMap<>();
        }
        options.put(key, value);
    }

    public void withOptions(Map<String, Object> options) {
        if (this.options == null) {
            this.options = new HashMap<>();
        }
        this.options.putAll(options);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        SerializeUtils.writeNullableUTF(type, out);
        SerializeUtils.writeNullableUTF(id, out);
        SerializeUtils.writeObject(options, out);
    }

    @Override
    @SuppressWarnings("all")
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        type = SerializeUtils.readNullableUTF(in);
        id = SerializeUtils.readNullableUTF(in);
        options = (Map<String, Object>) SerializeUtils.readObject(in);
    }
}
