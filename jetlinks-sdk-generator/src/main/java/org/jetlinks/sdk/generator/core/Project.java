package org.jetlinks.sdk.generator.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Project extends Artifact {
    private Parent parent;

    private String name;
    private String description;
}
