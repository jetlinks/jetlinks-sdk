package org.jetlinks.sdk.server.ai.llm;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.sdk.server.ai.GenericAiOutput;
import org.jetlinks.sdk.server.ai.llm.chat.ChatMessageReply;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

@Getter
@Setter
public class ChatOutput extends GenericAiOutput<ChatOutput> {

    private ChatMessageReply message;

    @JsonIgnore
    private Map<String, Object> others;

    @JsonAnyGetter
    public Map<String, Object> getOthers() {
        return others;
    }

    @JsonAnySetter
    public void withOther(String key, Object value) {
        if (others == null) {
            others = new java.util.HashMap<>();
        }
        others.put(key, value);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeBoolean(message != null);
        if (message != null) {
            message.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        if (in.readBoolean()) {
            message = new ChatMessageReply();
            message.readExternal(in);
        }
    }
}
