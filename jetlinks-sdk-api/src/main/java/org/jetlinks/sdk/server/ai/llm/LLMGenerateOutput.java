package org.jetlinks.sdk.server.ai.llm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.utils.SerializeUtils;
import org.jetlinks.sdk.server.ai.GenericAiOutput;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * 大模型生成输出结果
 *
 * @author zhouhao
 * @since 1.0.1
 */
@Getter
@Setter
public class LLMGenerateOutput extends GenericAiOutput<LLMGenerateOutput> {

    @Schema()
    private Object response;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        SerializeUtils.writeObject(response, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        response = SerializeUtils.readObject(in);
    }
}
