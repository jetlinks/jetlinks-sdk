package org.jetlinks.sdk.server.notify.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetlinks.core.command.AbstractCommand;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Schema(title = "发送消息通知")
public class SendNotifyCommand extends AbstractCommand<Mono<Void>, SendNotifyCommand> {

    @Schema(title = "通知配置ID")
    public String getNotifierId() {
        return (String) readable().get("notifierId");
    }

    @Schema(title = "模版ID")
    public String getTemplateId() {
        return (String) readable().get("templateId");
    }

    @Schema(title = "通知模版变量")
    @SuppressWarnings("all")
    public Map<String, Object> getVariable() {
        return (Map<String, Object>) readable()
            .getOrDefault("variable", Collections.emptyMap());
    }

    @Schema(title = "业务类型")
    public String getBizType() {
        return getOrNull("bizType", String.class);
    }

    @Schema(title = "业务ID")
    public String getBizId() {
        return getOrNull("bizId", String.class);
    }

    @Schema(title = "描述")
    public String getDescription() {
        return getOrNull("description", String.class);
    }

    @Schema(title = "业务拓展信息")
    @SuppressWarnings("all")
    public Map<String, Object> getBizExt() {
        return (Map<String, Object>) readable().getOrDefault("bizExt", Collections.emptyMap());
    }

    public SendNotifyCommand withBizExt(Map<String, Object> bizExt) {
        writable().put("bizExt", bizExt);
        return castSelf();
    }

    @SuppressWarnings("all")
    public SendNotifyCommand withBizExt(String key, Object value) {
        ((Map<String, Object>) writable()
            .computeIfAbsent("bizExt", ignore -> new HashMap<>()))
            .put(key, value);
        return castSelf();
    }

    public SendNotifyCommand withNotifierId(String notifierId) {
        writable().put("notifierId", notifierId);
        return castSelf();
    }

    public SendNotifyCommand withTemplateId(String templateId) {
        writable().put("templateId", templateId);
        return castSelf();
    }

    public SendNotifyCommand withVariable(Map<String, Object> variable) {
        writable().put("variable", variable);
        return castSelf();
    }

    @SuppressWarnings("all")
    public SendNotifyCommand withVariable(String key, Object value) {
        ((Map<String, Object>) writable()
            .computeIfAbsent("variable", ignore -> new HashMap<>()))
            .put(key, value);
        return castSelf();
    }
}
