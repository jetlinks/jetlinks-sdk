package org.jetlinks.sdk.server.notify.cmd;

import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

public class SendNotifyCommand extends AbstractCommand<Mono<Void>, SendNotifyCommand> {

    public String getNotifierId() {
        return (String) readable().get("notifierId");
    }

    public String getTemplateId() {
        return (String) readable().get("templateId");
    }

    @SuppressWarnings("all")
    public Map<String, Object> getContext() {
        return (Map<String, Object>) readable().getOrDefault("context", Collections.emptyMap());
    }

    public SendNotifyCommand withNotifierId(String notifierId) {
        writable().put("notifierId", notifierId);
        return castSelf();
    }

    public SendNotifyCommand withTemplateId(String templateId) {
        writable().put("templateId", templateId);
        return castSelf();
    }

    public SendNotifyCommand withContext(Map<String, Object> context) {
        writable().put("context", context);
        return castSelf();
    }
}
