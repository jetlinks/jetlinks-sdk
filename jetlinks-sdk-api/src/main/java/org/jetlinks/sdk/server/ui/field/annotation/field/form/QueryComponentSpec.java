package org.jetlinks.sdk.server.ui.field.annotation.field.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jetlinks.core.metadata.PropertyMetadata;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class QueryComponentSpec extends FormComponentSpec {

    private final static String TYPE = "query";

    private String key = StringUtils.EMPTY;

    private List<PropertyMetadata> fields;

    @Override
    protected String getComponentType() {
        return TYPE;
    }

    @Override
    protected void appendComponentConfig(Map<String, Object> component) {
        component.put("fields", fields);
        component.put("key", key);
    }
}

