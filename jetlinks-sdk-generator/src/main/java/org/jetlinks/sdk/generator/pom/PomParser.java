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


    static DefaultPomParser create(Path pomPath) {
        return new DefaultPomParser(pomPath);
    }

    MavenModel parse();

    Mono<Void> write(OutputStream stream);

    Mono<Void> write(Flux<DataBuffer> buffers);

}
