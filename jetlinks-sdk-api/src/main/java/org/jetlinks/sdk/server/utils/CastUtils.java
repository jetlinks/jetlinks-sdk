package org.jetlinks.sdk.server.utils;

import java.util.Collection;
import java.util.Collections;

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

    @SuppressWarnings("all")
    public static <T> Collection<T> castCollection(Object collection){
        if (collection == null) {
            return Collections.emptyList();
        }
        if (collection instanceof Collection) {
            return ((Collection<T>) collection);
        }
        return Collections.singleton((T)collection);
    }
}
