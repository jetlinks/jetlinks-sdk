package org.jetlinks.sdk.server.ai;

import java.util.Map;
import java.util.function.Supplier;


public class SimpleGenericAiOutput extends AbstractSimpleGenericAiOutput<SimpleFileData, SimpleSchemaData, SimpleGenericAiOutput> {

    public void setSchemaData(Map<String, Object> map) {
        if (map == null) {
            setSchemaData(new SimpleSchemaData());
        } else {
            setSchemaData(new SimpleSchemaData(map));
        }
    }

    public Supplier<SimpleFileData> newFileDataSupplier() {
        return SimpleFileData::new;
    }

    @Override
    public Supplier<SimpleSchemaData> newSchemaSupplier() {
        return SimpleSchemaData::new;
    }

}
