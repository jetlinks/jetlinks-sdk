package org.jetlinks.sdk.server.commons.cmd.metadata;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public abstract class QueryParamSpec {

    @Schema(title = "简化动态条件",
        description = "与terms不能共存,格式:{\"column[$termType][,$options]\",\"value\"},例如: {\"name$like\":\"zhang%\"}",
        example = "{\"name$like\":\"zhang%\"}")
    public Map<String, Object> filter;

    @Schema(title = "动态条件",
        example = "[{\"column\":\"name\",\"termType\":\"like\",\"value\":\"zhang%\"}]")
    private List<TermSpec> terms;

    @Schema(title = "排序", example = "[{\"name\":\"name\",\"order\":\"asc\"}]")
    private List<SortOrderSpec> sorts;

}
