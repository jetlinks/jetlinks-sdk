package org.jetlinks.sdk.server.ai.message;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.sdk.server.ai.AiOutput;
import org.jetlinks.sdk.server.ai.cv.ObjectDetectionResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ai目标检测消息  用于数据预处理
 */
@Getter
@Setter
public class AiDietedMessage extends CommonAiMessage<AiDietedMessage> {
    //转换为预处理匹配需要的数据
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
