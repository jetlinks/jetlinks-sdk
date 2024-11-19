package org.jetlinks.sdk.generator.pom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.maven.model.Profile;
import org.apache.maven.model.Repository;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class MavenModel {

    @NotBlank
    private String groupId;
    @NotBlank
    private String artifactId;
    @NotBlank
    private String version;

    private List<String> modules;

    private Map<String, String> properties;

    private List<Profile> profiles;

    private List<Repository> repositories;
}
