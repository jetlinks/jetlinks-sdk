package org.jetlinks.sdk.server.ai;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.jetlinks.core.utils.SerializeUtils;
import org.jetlinks.sdk.server.file.FileData;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter
@Setter
public abstract class SimpleGenericAiOutput<T extends FileData, SELF extends SimpleGenericAiOutput<T, SELF>> extends GenericAiOutput<SELF> {

    private Integer hitValue;

    private String textValue;

    private Double numberValue;

    private Map<String, Object> schemaMap;

    private List<T> fileData;

    public abstract Supplier<T> newFileDataSupplier();

    @Override
    public Map<String, Object> schemaMap() {
        return schemaMap;
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
        SerializeUtils.writeKeyValue(schemaMap, out);
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
        this.setSchemaMap(new HashMap<>());
        SerializeUtils.readKeyValue(in, this.schemaMap::put);
        int sizeImg = in.readInt();
        if (sizeImg > 0) {
            fileData = new ArrayList<>(sizeImg);
            for (int i = 0; i < sizeImg; i++) {
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
