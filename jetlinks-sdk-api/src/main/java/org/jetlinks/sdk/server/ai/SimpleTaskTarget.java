package org.jetlinks.sdk.server.ai;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Supplier;

@NoArgsConstructor
@Getter
@Setter
public class SimpleTaskTarget implements TaskTarget {
    private String value;
    private String text;
    private String description;

    public SimpleTaskTarget(String value, String text, String description) {
        this.value = value;
        this.text = text;
        this.description = description;
    }

    public static TaskTarget of(String value, String text, String description) {
        return new SimpleTaskTarget(value, text, description);
    }

    public static TaskTarget of(String value, String text) {
        return new SimpleTaskTarget(value, text, text);
    }

    @Override
    public Supplier<? extends AiOutput<?>> getAiOutputInstance() {
        return GenericAiOutput::new;
    }

    @Override
    public Supplier<? extends AiOutput<?>> getSimpleAiOutputInstance() {
        return GenericAiOutput::new;
    }
}
