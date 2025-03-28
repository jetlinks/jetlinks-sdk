package org.jetlinks.sdk.server.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
public class SimpleTaskTarget implements TaskTarget {
    private String value;
    private String text;
    private int ordinal;

    public static TaskTarget of(String value, String text) {
        return SimpleTaskTarget.of(value, text, 0);
    }

    @Override
    public int ordinal() {
        return ordinal;
    }
}
