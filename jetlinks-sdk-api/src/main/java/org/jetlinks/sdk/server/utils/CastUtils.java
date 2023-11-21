package org.jetlinks.sdk.server.utils;

public class CastUtils {

    public static boolean castBoolean(Object value) {
        if (value instanceof Boolean) {
            return ((Boolean) value);
        }
        String strVal = String.valueOf(value);

        return "true".equalsIgnoreCase(strVal) ||
                "y".equalsIgnoreCase(strVal) ||
                "ok".equalsIgnoreCase(strVal) ||
                "yes".equalsIgnoreCase(strVal) ||
                "1".equalsIgnoreCase(strVal);
    }
}
