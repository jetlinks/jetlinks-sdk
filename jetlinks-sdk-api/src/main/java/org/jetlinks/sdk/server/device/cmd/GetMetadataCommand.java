package org.jetlinks.sdk.server.device.cmd;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import org.jetlinks.core.things.ThingMetadata;
import org.jetlinks.supports.official.JetLinksDeviceMetadata;
import org.jetlinks.supports.official.JetLinksDeviceMetadataCodec;
import reactor.core.publisher.Mono;

import java.util.Map;

@Schema(title = "获取物模型")
public class GetMetadataCommand extends AbstractCommand<Mono<ThingMetadata>, GetMetadataCommand> {

    @Schema(title = "ID")
    public String getId() {
        return getOrNull("id", String.class);
    }

    public GetMetadataCommand withId(String id) {
        return with("id", id);
    }

    @Override
    @SuppressWarnings("all")
    public Object createResponseData(Object value) {
        if (value instanceof ThingMetadata) {
            return value;
        }
        if (value instanceof Map) {
            return new JetLinksDeviceMetadata(new JSONObject(((Map) value)));
        }
        if (value instanceof String) {
            return JetLinksDeviceMetadataCodec.getInstance().decode((String) value);
        }
        return super.createResponseData(value);
    }
}
