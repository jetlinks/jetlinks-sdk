package org.jetlinks.sdk.generator.pom;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class DefaultPomParser implements PomParser {

    private final static String POM_PREFIX = "pom.xml";

    private final DataBufferFactory bufferFactory = new DefaultDataBufferFactory();

    @Override
    @SneakyThrows
    public MavenModel parse(Path pomPath) {
        Path path = pomPath.resolve(POM_PREFIX);
        try (InputStreamReader fileReader = new InputStreamReader(Files.newInputStream(path), StandardCharsets.UTF_8)) {
            Model model = new MavenXpp3Reader().read(fileReader);
            return MavenModel.convertToMavenModel(model);
        }
    }


    @Override
    @SneakyThrows
    public Mono<MavenModel> parse(Flux<DataBuffer> buffers) {
        return DataBufferUtils.join(buffers)
                .flatMap(dataBuffer -> {
                    try (InputStream inputStream = dataBuffer.asInputStream(true)) {
                        Model model = new MavenXpp3Reader().read(inputStream);
                        return Mono.just(MavenModel.convertToMavenModel(model));
                    } catch (Exception e) {
                        return Mono.error(new RuntimeException("Error parsing POM file stream", e));
                    }
                });
    }

    @Override
    public Flux<DataBuffer> toFileStream(MavenModel mavenModel) {
        return Mono.fromCallable(() -> {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Model model = MavenModel.convertToModel(mavenModel);
            new MavenXpp3Writer().write(outputStream, model);
            byte[] bytes = outputStream.toByteArray();
            return bufferFactory.wrap(bytes);
        }).flux();
    }

    @Override
    public Mono<Void> write(OutputStream stream, MavenModel mavenModel) {
        return Mono.fromCallable(() -> {
                    Model model = MavenModel.convertToModel(mavenModel);
                    new MavenXpp3Writer().write(stream, model);
                    return null;
                })
                .onErrorResume(err -> Mono.error(new RuntimeException("Failed to write POM", err)))
                .subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Mono<Void> write(Flux<DataBuffer> buffers, Path pomPath) {
        return DataBufferUtils.write(buffers, pomPath)
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }
}
