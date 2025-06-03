package org.jetlinks.sdk.generator.pom;

import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.sdk.generator.core.Dependency;
import org.jetlinks.sdk.generator.core.Parent;
import org.jetlinks.sdk.generator.core.Project;
import org.jetlinks.sdk.server.utils.ConverterUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Getter
public class DefaultPomParser implements PomParser {

    @Getter
    private Model model;

    public DefaultPomParser() {
        this.model = new Model();
    }

    public DefaultPomParser(Model model) {
        this.model = model;
    }

    public static Mono<PomParser> parse(Flux<DataBuffer> buffers) {
        return DataBufferUtils
            .join(buffers)
            .flatMap(dataBuffer -> Mono.fromCallable(() -> {
                try (InputStream inputStream = dataBuffer.asInputStream(true)) {
                    Model model = new MavenXpp3Reader().read(inputStream);
                    return new DefaultPomParser(model);
                }
            }));
    }

    @Override
    public MavenPom parse() {
        return parse(model);
    }

    @Override
    public void update(MavenPom pom) {
        copyFromProject(pom.getProject());
        model.setModelVersion(pom.getModelVersion());
        model.setModules(pom.getModules());
        if (pom.getProperties() == null) {
            model.setProperties(new Properties());
        } else {
            model.setProperties(FastBeanCopier.copy(pom.getProperties(), new Properties()));
        }
        model.setProfiles(pom.getProfiles());
        model.setRepositories(pom.getRepositories());
        if (CollectionUtils.isNotEmpty(pom.getDependencies())) {
            List<org.apache.maven.model.Dependency> dependencyStream = pom
                .getDependencies()
                .stream()
                .map(d -> FastBeanCopier.copy(d, new org.apache.maven.model.Dependency()))
                .collect(Collectors.toList());
            model.setDependencies(dependencyStream);
        } else {
            model.setDependencies(Collections.emptyList());
        }
    }

    private void copyFromProject(Project project) {
        model.setGroupId(project.getGroupId());
        model.setArtifactId(project.getArtifactId());
        model.setVersion(project.getVersion());
        model.setName(project.getName());
        model.setDescription(project.getDescription());
        if (project.getParent() != null) {
            model.setParent(FastBeanCopier.copy(project.getParent(), new org.apache.maven.model.Parent()));
        } else {
            model.setParent(null);
        }

    }

    @Override
    public Flux<DataBuffer> toFileStream() {
        return Mono.fromCallable(() -> {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            new MavenXpp3Writer().write(outputStream, model);
            byte[] bytes = outputStream.toByteArray();
            return ConverterUtils.convertDataBuffer(bytes);
        }).flux();
    }

    @Override
    public PomParser copy() {
        return new DefaultPomParser(model.clone());
    }


    private static MavenPom parse(Model model) {
        MavenPom mavenPom = new MavenPom();
        mavenPom.setProject(copyProject(model));
        mavenPom.setModelVersion(model.getModelVersion());
        mavenPom.setModules(model.getModules());
        mavenPom.setProperties(FastBeanCopier.copy(model.getProperties(), new HashMap<>()));
        mavenPom.setProfiles(model.getProfiles());
        mavenPom.setRepositories(model.getRepositories());
        List<Dependency> dependencyStream = model
            .getDependencies()
            .stream()
            .map(d -> FastBeanCopier.copy(d, new Dependency()))
            .collect(Collectors.toList());
        mavenPom.setDependencies(dependencyStream);
        return mavenPom;
    }

    private static Project copyProject(Model model) {
        Project project = new Project();
        project.setGroupId(model.getGroupId());
        project.setArtifactId(model.getArtifactId());
        project.setVersion(model.getVersion());
        project.setDescription(model.getDescription());
        project.setName(model.getName());
        if (model.getParent() != null) {
            project.setParent(FastBeanCopier.copy(model.getParent(), new Parent()));
        }
        return project;
    }
}
