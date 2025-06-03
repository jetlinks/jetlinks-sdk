package org.jetlinks.sdk.generator.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class Project extends Artifact {
    private Parent parent;

    private String name;
    private String description;

    public void merge(Project project) {
        super.merge(project);
        if (StringUtils.hasText(project.getName())) {
            this.name = project.getName();
        }
        if (StringUtils.hasText(project.getDescription())) {
            this.description = project.getDescription();
        }
        if (project.getParent() != null) {
            this.parent.merge(project.getParent());
        }
    }
}
