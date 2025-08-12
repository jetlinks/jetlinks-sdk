package org.jetlinks.sdk.server.collector;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.dict.I18nEnumDict;

@AllArgsConstructor
@Getter
public enum PointCollectorState implements I18nEnumDict<String> {

    running("采集中"),
    error("采集失败"),
    stopped("已停止")
    ;

    @Override
    public String getValue() {
        return name();
    }

    private final String text;

}
