package org.jetlinks.sdk.server.ai.embedding;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.sdk.server.ai.GenericAiOutput;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;


@Getter
@Setter
public class TextEmbedding extends GenericAiOutput<TextEmbedding> {

    @Schema(title = "向量数据")
    private List<Vector> embedding;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);

        if (embedding == null) {
            out.writeInt(-1);
        } else {
            out.writeInt(embedding.size());
            for (Vector vector : embedding) {
                vector.writeExternal(out);
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);

        int len = in.readInt();
        if (len >= 0) {
            embedding = new java.util.ArrayList<>(len);
            for (int i = 0; i < len; i++) {
                Vector vector = new Vector();
                vector.readExternal(in);
                embedding.add(vector);
            }
        }
    }
}
