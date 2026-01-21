package org.jetlinks.sdk.server.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hswebframework.web.i18n.LocaleUtils;
import org.jetlinks.sdk.server.ai.cv.ComputerVisionCommand;
import org.jetlinks.sdk.server.ai.cv.ObjectDetectionCommand;
import org.jetlinks.sdk.server.ai.cv.ObjectDetectionResult;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.function.Supplier;

@Getter
public enum InternalCVTaskTarget implements TaskTarget {

    /* =- 计算机视觉 -= */

    /**
     * @see ObjectDetectionResult
     * @see org.jetlinks.sdk.server.ai.cv.ObjectDetectionCommand
     */
    ObjectDetection,
    ImageRecognition,
    HelmetDetection,
    WorkwearDetection,
    SmokingBehaviorDetection,
    FireDetection,
    SmokeDetection,
    IntrusionDetection,
    FallDetection,
    VehicleReverseDrivingDetection,
    IllegalParkingDetection,
    HumanVehicleMixingDetection,

    ;
    private final String text;
    private final String description;

    InternalCVTaskTarget() {
        this.text = "message.task_target_" + getValue() + "_text";
        this.description = "message.task_target_" + getValue() + "_description";
    }

    InternalCVTaskTarget(String text, String description) {
        this.text = text;
        this.description = description;
    }


    public String getText() {
        return LocaleUtils.resolveMessage(text, text);
    }

    public String getDescription() {
        return LocaleUtils.resolveMessage(description, description);
    }

    @Override
    public Supplier<? extends AiOutput<?>> getAiOutputInstance() {
        return ObjectDetectionResult::new;
    }

    @Override
    public Mono<AiOutputMetadata> getAiOutputMetadata() {
        return Mono.just(ObjectDetectionCommand.aiOutputMetadata);
    }

    @Override
    public String getValue() {
        return name();
    }

    public  SimpleComputerVisionCommand createCommand() {
        return new SimpleComputerVisionCommand(this.getValue());
    }

    public static  SimpleComputerVisionCommand createCommand(TaskTarget target) {
        return new SimpleComputerVisionCommand(target.getValue());
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SimpleComputerVisionCommand extends ComputerVisionCommand<ObjectDetectionResult, SimpleComputerVisionCommand> {
        private String taskTarget;

        @Override
        public String getCommandId() {
            return taskTarget;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeUTF(taskTarget);
            super.writeExternal(out);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            taskTarget = in.readUTF();
            super.readExternal(in);
        }
    }
}
