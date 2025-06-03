package org.jetlinks.sdk.server.ui.field;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class UIField implements Serializable {
    private static final long serialVersionUID = 1L;

    private String field;
    private String label;
    private int order;
    private String type;
    private Map<String, Object> config;
    private Set<UIScopeType> scopes;
    private Map<String, Object> expands;

    private String selectorType;
    private Map<String, Object> selectorConfig;

}
