package org.jetlinks.sdk.server.ai;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.utils.SerializeUtils;
import org.jetlinks.sdk.server.file.FileData;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter
@Setter
public abstract class AbstractSimpleGenericAiOutput<T extends FileData, V extends Externalizable, SELF extends AbstractSimpleGenericAiOutput<T, V, SELF>> extends GenericAiOutput<SELF> {

    private Integer hitValue;

    private String textValue;

    private Double numberValue;

    private V schemaData;

    private List<T> fileData;

    public abstract Supplier<T> newFileDataSupplier();

    public abstract Supplier<V> newSchemaSupplier();

    @Override
    public Map<String, Object> schemaMap() {
        if(schemaData == null) {
            return Collections.emptyMap();
        }
        return FastBeanCopier.copy(schemaData, new HashMap<>());
    }

    @Override
    public List<? extends FileData> files() {
        return fileData;
    }

    @Override
    public String text() {
        return textValue;
    }

    @Override
    public Integer hitValue() {
        return hitValue;
    }

    @Override
    public JSONObject toJson(){
        JSONObject map = FastBeanCopier.copy(this, new JSONObject(), "fileData");
        map.put("fileData", fileDataToJson());
        return map;
    }

    private List<Map<String, Object>> fileDataToJson() {
        if (CollectionUtils.isNotEmpty(fileData)) {
            //移除原始数据
            List<Map<String, Object>> datas = new ArrayList<>(fileData.size());
            for (FileData data : fileData) {
                datas.add(FastBeanCopier.copy(data, new HashMap<>(), "data"));
            }
            return datas;
        }
        return null;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        writeNullable(hitValue, out, v -> {
            try {
                out.writeInt(v);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        SerializeUtils.writeNullableUTF(textValue, out);
        writeNullable(numberValue, out, v -> {
            try {
                out.writeDouble(v);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writeNullable(schemaData, out, v -> {
            try {
                schemaData.writeExternal(out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        if (CollectionUtils.isEmpty(fileData)) {
            out.writeInt(0);
        } else {
            out.writeInt(fileData.size());
            for (FileData file : fileData) {
                file.writeExternal(out);
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        hitValue = readNullable(in, () -> {
            try {
                return in.readInt();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        textValue = SerializeUtils.readNullableUTF(in);
        numberValue = readNullable(in, () -> {
            try {
                return in.readDouble();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        if (!in.readBoolean()) {
            V schemaData = newSchemaSupplier().get();
            schemaData.readExternal(in);
        }
        int sizeFile = in.readInt();
        if (sizeFile > 0) {
            fileData = new ArrayList<>(sizeFile);
            for (int i = 0; i < sizeFile; i++) {
                T file = newFileDataSupplier().get();
                file.readExternal(in);
                fileData.add(file);
            }
        } else {
            fileData = new ArrayList<>(0);
        }
    }

    public static <T> void writeNullable(T value, ObjectOutput out, Consumer<T> write) throws IOException {
        if (value == null) {
            out.writeBoolean(true);
            return;
        }
        out.writeBoolean(false);
        write.accept(value);
    }

    public static <T> T readNullable(ObjectInput in, Supplier<T> reader) throws IOException {
        if (in.readBoolean()) {
            return null;
        }
        return reader.get();
    }


}
