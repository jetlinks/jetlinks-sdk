package org.jetlinks.sdk.generator.pom;

import lombok.*;
import org.apache.maven.model.Profile;
import org.apache.maven.model.Repository;
import org.jetlinks.sdk.generator.core.Dependency;
import org.jetlinks.sdk.generator.core.Project;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
public class MavenPom {

    private String modelVersion = "4.0.0";

    @NotBlank
    private Project project;

    private List<String> modules;

    private Map<String, String> properties;

    private List<Profile> profiles;

    private List<Repository> repositories;

    private List<Dependency> dependencies;

}
