package org.jetlinks.sdk.generator.pom;

import lombok.*;
import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import org.apache.maven.model.Repository;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.sdk.generator.core.Dependency;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
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

    private List<Dependency> dependencies;

    public static MavenModel convertToMavenModel(Model model) {
        MavenModel mavenModel = new MavenModel();
        mavenModel.setGroupId(model.getGroupId());
        mavenModel.setArtifactId(model.getArtifactId());
        mavenModel.setVersion(model.getVersion());
        mavenModel.setModules(model.getModules());
        mavenModel.setProperties(FastBeanCopier.copy(model.getProperties(), new HashMap<>()));
        mavenModel.setProfiles(model.getProfiles());
        mavenModel.setRepositories(model.getRepositories());
        List<Dependency> dependencyStream = model.getDependencies().stream()
                .map(d -> FastBeanCopier.copy(d, new Dependency())).collect(Collectors.toList());
        mavenModel.setDependencies(dependencyStream);
        return mavenModel;
    }

    public static Model convertToModel(MavenModel mavenModel) {
        Model model = new Model();
        model.setGroupId(mavenModel.getGroupId());
        model.setArtifactId(mavenModel.getArtifactId());
        model.setVersion(mavenModel.getVersion());
        model.setModules(mavenModel.getModules());
        if (mavenModel.getProperties() == null) {
            model.setProperties(new Properties());
        } else {
            model.setProperties(FastBeanCopier.copy(mavenModel.getProperties(), new Properties()));
        }
        model.setProfiles(mavenModel.getProfiles());
        model.setRepositories(mavenModel.getRepositories());
        List<org.apache.maven.model.Dependency> dependencyStream = mavenModel.getDependencies().stream()
                .map(d -> FastBeanCopier.copy(d, new org.apache.maven.model.Dependency())).collect(Collectors.toList());
        model.setDependencies(dependencyStream);
        return model;
    }
}
