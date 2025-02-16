package org.jetlinks.sdk.server.ai.llm.chat;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.sdk.server.ai.llm.tools.ToolCall;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatMessageReply extends ChatMessage {

    @JsonAlias({"toolCalls", "tool_calls"})
    private List<ToolCall> toolCalls;

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);

        int size = in.readInt();
        toolCalls = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ToolCall toolCall = new ToolCall();
            toolCall.readExternal(in);
            toolCalls.add(toolCall);
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);

        int size = toolCalls == null ? 0 : toolCalls.size();
        out.writeInt(size);
        if (size > 0) {
            for (ToolCall toolCall : toolCalls) {
                toolCall.writeExternal(out);
            }
        }
    }
}
