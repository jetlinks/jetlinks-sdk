package org.jetlinks.sdk.server.ui.field.annotation.field.form;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public abstract class FormComponentSpec {

    private final static String key = "formComponent";

    protected abstract String getComponentType();

    public Map<String, Object> toExpands() {
        Map<String, Object> component = new LinkedHashMap<>();
        component.put("component", getComponentType());
        appendComponentConfig(component);

        Map<String, Object> expands = new LinkedHashMap<>();
        expands.put(key, component);
        return expands;
    }

    protected abstract void appendComponentConfig(Map<String, Object> component);

}
