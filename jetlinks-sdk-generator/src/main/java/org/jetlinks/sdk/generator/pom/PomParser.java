package org.jetlinks.sdk.generator.pom;

import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * POM文件解析.
 */
public interface PomParser {


    static PomParser create() {
        return new DefaultPomParser();
    }

    /**
     * 加载pom文件流为解析对象
     *
     * @param buffers
     * @return
     */
    static Mono<PomParser> load(Flux<DataBuffer> buffers) {
        return DefaultPomParser.parse(buffers);
    }

    /**
     * 解析pom信息
     *
     * @return pom信息
     */
    MavenPom parse();

    /**
     * 更新pom信息
     *
     * @param pom pom信息
     */
    void update(MavenPom pom);

    /**
     * 加载为文件流
     *
     * @return 文件流
     */
    Flux<DataBuffer> toFileStream();

    /**
     * 复制pom解析对象
     *
     * @return
     */
    PomParser copy();

}
