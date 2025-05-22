package org.jetlinks.sdk.server.ui.field.annotation.field.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.metadata.PropertyMetadata;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class QueryComponentSpec extends FormComponentSpec {

    private final String key = "query";

    private List<PropertyMetadata> fields;

    @Override
    protected String getComponentType() {
        return key;
    }

    @Override
    protected void appendComponentConfig(Map<String, Object> component) {
        component.put("fields", fields);
    }
}

