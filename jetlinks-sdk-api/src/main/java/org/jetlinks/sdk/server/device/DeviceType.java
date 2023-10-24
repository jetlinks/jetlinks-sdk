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
public enum DeviceType implements I18nEnumDict<String> {
    device("直连设备"),
    childrenDevice("网关子设备"),
    gateway("网关设备")
    ;

    private final String text;

    @Override
    public String getValue() {
        return name();
    }


}



