package org.jetlinks.sdk.server.ai.cv;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.utils.SerializeUtils;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author gyl
 * @since 1.0.1
 */
public class AiTaskCommandResult<SELF> extends AiCommandResult<AiTaskCommandResult<SELF>> {

    @Schema(title = "任务ID")
    private String taskId;


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        SerializeUtils.writeNullableUTF(taskId, out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        taskId = SerializeUtils.readNullableUTF(in);

    }
}
