package org.jetlinks.sdk.server.device;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import org.hswebframework.web.dict.Dict;
import org.hswebframework.web.dict.EnumDict;
import org.hswebframework.web.dict.I18nEnumDict;

@AllArgsConstructor
@Getter
@Generated
@JsonDeserialize(contentUsing = EnumDict.EnumDictJSONDeserializer.class)
public enum DeviceState implements I18nEnumDict<String> {
    notActive("禁用"),
    offline("离线"),
    online("在线");

    private final String text;

    @Override
    public String getValue() {
        return name();
    }

}
