package org.jetlinks.sdk.generator.pom;

import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.OutputStream;
import java.nio.file.Path;

/**
 * POM文件解析.
 *
 */
public interface PomParser {


    static DefaultPomParser create() {
        return new DefaultPomParser();
    }

    MavenModel parse(Path pomPath);

    Mono<MavenModel> parse(Flux<DataBuffer> dataBufferFlux);

    Flux<DataBuffer> toFileStream(MavenModel model);

    Mono<Void> write(OutputStream stream, MavenModel model);

    Mono<Void> write(Flux<DataBuffer> buffers, Path path);

}
