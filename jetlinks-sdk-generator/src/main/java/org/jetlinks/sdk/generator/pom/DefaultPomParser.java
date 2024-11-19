package org.jetlinks.sdk.generator.pom;

import lombok.SneakyThrows;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.hswebframework.web.bean.FastBeanCopier;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class DefaultPomParser implements PomParser {

    private final static String POM_PREFIX = "pom.xml";

    private final Path pomPath;

    private final Model model;

    @SneakyThrows
    public DefaultPomParser(Path pomPath) {
        this.pomPath = pomPath.resolve(POM_PREFIX);
        try (InputStreamReader fileReader = new InputStreamReader(Files.newInputStream(pomPath), StandardCharsets.UTF_8)) {
            this.model = new MavenXpp3Reader().read(fileReader);
        }
    }

    @Override
    public MavenModel parse() {
        MavenModel mavenModel = new MavenModel();
        mavenModel.setGroupId(model.getGroupId());
        mavenModel.setArtifactId(model.getArtifactId());
        mavenModel.setVersion(model.getVersion());
        mavenModel.setModules(model.getModules());
        mavenModel.setProperties(FastBeanCopier.copy(model.getProperties(), new HashMap<>()));
        mavenModel.setProfiles(model.getProfiles());
        mavenModel.setRepositories(model.getRepositories());
        return mavenModel;
    }

    @Override
    public Mono<Void> write(OutputStream stream) {
        return Mono.fromCallable(() -> {
                    new MavenXpp3Writer().write(stream, model);
                    return null;
                })
                .onErrorResume(err -> Mono.error(new RuntimeException("Failed to write POM", err)))
                .subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Mono<Void> write(Flux<DataBuffer> buffers) {
        return DataBufferUtils.write(buffers, pomPath)
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }
}
