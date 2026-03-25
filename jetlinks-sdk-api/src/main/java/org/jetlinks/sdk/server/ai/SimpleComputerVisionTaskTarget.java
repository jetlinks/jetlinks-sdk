package org.jetlinks.sdk.server.ai;

import org.jetlinks.sdk.server.ai.cv.ObjectDetectionResult;

import java.util.function.Supplier;


public class SimpleComputerVisionTaskTarget extends SimpleTaskTarget {

    public SimpleComputerVisionTaskTarget() {
    }

    public SimpleComputerVisionTaskTarget(String id, String text, String description) {
        super(id, text, description);
    }

    public static TaskTarget of(String id, String value, String text) {
        return new SimpleComputerVisionTaskTarget(id, value, text);
    }

    public static TaskTarget of(String value, String text) {
        return new SimpleComputerVisionTaskTarget(value, text, text);
    }

    @Override
    public Supplier<? extends AiOutput<?>> getAiOutputInstance() {
        return ObjectDetectionResult::new;
    }

    @Override
    public Supplier<? extends AiOutput<?>> getSimpleAiOutputInstance() {
        return SimpleImageGenericAiOutput::new;
    }
}
