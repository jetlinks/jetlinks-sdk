package org.jetlinks.sdk.server.device;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import org.hswebframework.web.dict.I18nEnumDict;

/**
 * 固件升级方式.
 *
 * @author zhangji 2024/9/29
 * @since 2.3
 */
@AllArgsConstructor
@Getter
@Generated
public enum FirmwareUpgradeMode implements I18nEnumDict<String> {

    pull("设备拉取"),
    push("平台推送");

    private final String text;

    @Override
    public String getValue() {
        return name();
    }
}
