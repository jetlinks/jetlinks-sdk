package org.jetlinks.sdk.server.ai.llm.tools;

import lombok.Getter;
import lombok.Setter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

@Getter
@Setter
public class ToolCall implements Externalizable {
    private static final byte UNKNOWN = 0;

    private static final byte FUNCTION = 1;

    private FunctionCall function;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        if (function != null) {
            out.writeByte(FUNCTION);
            function.writeExternal(out);
        } else {
            out.writeByte(UNKNOWN);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        if (in.readByte() == FUNCTION) {
            function = new FunctionCall();
            function.readExternal(in);
        }
    }
}
