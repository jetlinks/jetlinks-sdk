package org.jetlinks.sdk.server.role;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import org.hswebframework.web.dict.EnumDict;
import org.hswebframework.web.dict.I18nEnumDict;

@Getter
@AllArgsConstructor
@Generated
@JsonDeserialize(contentUsing = EnumDict.EnumDictJSONDeserializer.class)
public enum RoleState implements EnumDict<String> {

    enabled("正常"),
    disabled("已禁用");

    private final String text;

    @Override
    public String getValue() {
        return name();
    }

}
