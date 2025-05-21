package org.jetlinks.sdk.server.ui.field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetlinks.core.metadata.PropertyMetadata;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class FormComponentInfo {

    private ComponentType component;

    private List<PropertyMetadata> fields;

    public enum ComponentType {
        query
    }

}

