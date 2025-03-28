package org.jetlinks.sdk.server.commons.cmd.metadata;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermSpec {

    @Schema(title = "属性名")
    private String column;

    @Schema(title = "条件类型", description = "如:like,gt,lt")
    private String termType;

    @Schema(title = "条件值")
    private Object value;
}