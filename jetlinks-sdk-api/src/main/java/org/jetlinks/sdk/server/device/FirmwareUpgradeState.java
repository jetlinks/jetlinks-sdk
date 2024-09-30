package org.jetlinks.sdk.server.device;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import org.hswebframework.web.dict.I18nEnumDict;

/**
 * 固件升级状态.
 *
 * @author zhangji 2024/9/29
 * @since 2.3
 */
@AllArgsConstructor
@Getter
@Generated
public enum FirmwareUpgradeState implements I18nEnumDict<String> {

    waiting("等待升级"),
    processing("升级中"),
    failed("升级失败"),
    success("升级完成"),
    canceled("已停止");

    private final String text;

    @Override
    @Generated
    public String getValue() {
        return name();
    }
}
