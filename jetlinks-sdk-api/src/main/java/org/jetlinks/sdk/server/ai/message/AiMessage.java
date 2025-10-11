package org.jetlinks.sdk.server.ai.message;

import org.jetlinks.core.message.ThingMessage;
import org.jetlinks.sdk.server.ai.AiOutput;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface AiMessage extends ThingMessage {
    /**
     * ai消息预处理操作
     *
     * @return 新的消息
     */
    Mono<ThingMessage> handleMessage();

    /**
     * 获取ai数据
     *
     * @return ai输出结果
     */
    AiOutput<?> getData();

    /**
     * 获取ai配置项
     */
    Map<String, Object> getConfiguration();
}
