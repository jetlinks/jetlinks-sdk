package org.jetlinks.sdk.server.commons.cmd.metadata;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortOrderSpec {
    @Schema(title = "字段名")
    private String name;

    @Schema(title = "排序方式", allowableValues = {"asc", "desc"}, minLength = 3, maxLength = 4)
    private String order = "asc";

    @Schema(title = "指定的值优先排序")
    private Object value;
}
