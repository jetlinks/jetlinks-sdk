package org.jetlinks.sdk.server.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetlinks.sdk.server.ai.cv.ImageRecognitionCommand;
import org.jetlinks.sdk.server.ai.cv.ObjectDetectionCommand;
import org.jetlinks.sdk.server.ai.cv.ObjectDetectionResult;

import javax.annotation.Nullable;

@AllArgsConstructor
@Getter
public enum InternalTaskTarget implements TaskTarget {

    /* =- 计算机视觉 -= */

    /**
     * @see ObjectDetectionResult
     * @see org.jetlinks.sdk.server.ai.cv.ObjectDetectionCommand
     */
    ObjectDetection("目标检测", new ObjectDetectionCommand(), ObjectDetectionCommand.aiOutputMetadata),

    ImageRecognition("图像识别", new ImageRecognitionCommand(), ObjectDetectionCommand.aiOutputMetadata),

    /* =- 自然语言处理 -= */
    TextClassification("文本分类"),
    SpeechRecognition("语音识别"),
    TextGeneration("文本生成"),
    LLM("大语言模型"),
    LLMAgent("大语言模型智能体"),
    /* =- 语音处理 -= */
    SpeechSynthesis("语音合成"),
    SpeechTranslation("语音翻译"),

    ;
    private final String text;
    @Nullable
    private final AiCommand<?> command;
    private final AiOutputMetadata aiOutputMetadata;

    InternalTaskTarget(String text) {
        this.text = text;
        this.command = null;
        this.aiOutputMetadata = null;
    }


    @Override
    public String getValue() {
        return name();
    }


}
