package org.jetlinks.sdk.server.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetlinks.sdk.server.ai.cv.ObjectDetectionResult;

@AllArgsConstructor
@Getter
public enum InternalTaskTarget implements TaskTarget {

    /* =- 计算机视觉 -= */

    /**
     * 识别图像中的物体并为其标注边界框，可能返回多个检测结果
     * 不仅涉及到确定物体的类别（如人、车、动物等），还需要准确定位物体在图像中的位置
     * @see ObjectDetectionResult
     * @see org.jetlinks.sdk.server.ai.cv.ObjectDetectionCommand
     */
    ObjectDetection("目标检测"),

    /**
     *  也称【图像分类】，将整个图像分为特定类别
     *  模型会分析图像并输出一个标签，表示图像的主要内容
     *  比如，将一张图像标记为“猫”或“狗”，而不关注具体的物体位置
     */
    ImageRecognition("图像识别"),

    /**
     * 评估两张图像之间相似性或差异性
     * 它可以用于多种应用，如图像检索、变化检测或验证相似图像
     * 比对可以基于特征提取或深度学习模型来实现，例如人脸识别
     */
    ImageComparison("图像比对"),

    /* =- 自然语言处理 -= */
    TextClassification("文本分类"),
    SpeechRecognition("语音识别"),
    TextGeneration("文本生成"),

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
