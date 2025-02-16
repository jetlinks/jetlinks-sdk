package org.jetlinks.sdk.server.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetlinks.sdk.server.ai.cv.ObjectDetectionResult;

@AllArgsConstructor
@Getter
public enum InternalTaskTarget implements TaskTarget {

    /* =- 计算机视觉 -= */

    /**
     * @see ObjectDetectionResult
     * @see org.jetlinks.sdk.server.ai.cv.ObjectDetectionCommand
     */
    ObjectDetection("目标检测"),

    ImageRecognition("图像识别"),
    ImageEmbedding("图片向量化"),

    /* =- 自然语言处理 -= */
    TextClassification("文本分类"),
    SpeechRecognition("语音识别"),
    TextGeneration("文本生成"),
    TextEmbedding("文本向量化"),

    /* =- 语音处理 -= */
    SpeechSynthesis("语音合成"),
    SpeechTranslation("语音翻译"),

    ;
    private final String text;

    @Override
    public String getValue() {
        return name();
    }


}
