package org.jetlinks.sdk.server.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author gyl
 * @since 2.2
 */
@Getter
@Setter
public class DimensionUserBindInfo extends DimensionUserBindRequest {
    private List<String> userId;
}
