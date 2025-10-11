package org.jetlinks.sdk.server.ai.message;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.GenericHeaderSupport;
import org.jetlinks.core.message.ThingMessage;
import org.jetlinks.sdk.server.ai.AiOutput;
import org.jetlinks.sdk.server.ai.cv.ObjectDetectionResult;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * AI任务消息
 */
@Getter
@Setter
public class CommonAiMessage<SELF extends CommonAiMessage<SELF>> extends GenericHeaderSupport<SELF> implements AiMessage {
    private static final long serialVersionUID = -6849794470754667710L;
    private final String AI_TASK = "device";
    private String taskId;
    private String modelId;
    private String messageId;
    private long timestamp = System.currentTimeMillis();
    private AiOutput<?> data;
    private Map<String, Object> configuration;


    @Override
    public String getThingType() {
        return AI_TASK;
    }

    @Override
    public String getThingId() {
        return taskId;
    }

    @Override
    public SELF messageId(String messageId) {
        setMessageId(messageId);
        return castSelf();
    }


    @Override
    public SELF thingId(String thingType, String thingId) {
        setTaskId(thingId);
        return castSelf();
    }

    @Override
    public SELF timestamp(long timestamp) {
        this.timestamp = timestamp;
        return castSelf();
    }


    @Override
    public Mono<ThingMessage> handleMessage() {
        return Mono.just(this);
    }
    public Map<String, Object> covertPreprocessMatcherData() {
        AiOutput<?> data = this.getData();
        ObjectDetectionResult result = (ObjectDetectionResult) data;
        List<ObjectDetectionResult.DetectedObject> list = result.getObjects();
        Map<String, Object> map = result.toLightWeighMap();
        //统计所有标签的个数
        map.put("所有目标.number", list.size());
        //统计所有对象的置信度最大值
        Optional<Float> max = list.stream()
            .map(ObjectDetectionResult.DetectedObject::getScore)
            .max(Float::compare);
        max.ifPresent(scoreMax -> map.put("所有目标.score", scoreMax));
        //按标签分类
        Map<String, List<ObjectDetectionResult.DetectedObject>> classification = list
            .stream()
            .collect(Collectors.groupingBy(ObjectDetectionResult.DetectedObject::getLabel));

        for (String label : classification.keySet()) {
            map.put(label + ".number", classification.get(label).size());
            List<ObjectDetectionResult.DetectedObject> list1 = classification.get(label);

            Optional<Float> max1 = list1.stream()
                .map(ObjectDetectionResult.DetectedObject::getScore)
                .max(Float::compare);
            max1.ifPresent(s -> map.put(label + ".score", s));
        }
        return map;
    }
}
