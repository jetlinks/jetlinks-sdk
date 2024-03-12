package org.jetlinks.sdk.server.commons;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Date;

/**
 * 聚合查询条件.
 *
 * @author zhangji 2024/1/18
 */
@Getter
@Setter
public class AggregationRequest implements Serializable {
    private static final long serialVersionUID = 3667866475408489007L;

    //时间间隔
    //为空时,不按时间分组
    @Schema(description = "间隔,如: 1d", type = "string", defaultValue = "1d")
    @Nullable
    String interval = "1d";

    //时间格式
    @Schema(defaultValue = "时间格式,如:yyyy-MM-dd", description = "yyyy-MM-dd")
    String format = "yyyy-MM-dd";

    @Schema(description = "时间从,如: 2020-09-01 00:00:00,支持表达式: now-1d")
    Date from = new DateTime()
        .plusMonths(-1)
        .withHourOfDay(0)
        .withMinuteOfHour(0)
        .withSecondOfMinute(0)
        .toDate();

    @Schema(description = "时间到,如: 2020-09-30 00:00:00,支持表达式: now-1d")
    Date to = new DateTime()
        .withHourOfDay(23)
        .withMinuteOfHour(59)
        .withSecondOfMinute(59)
        .toDate();

    @Schema(description = "数量限制")
    Integer limit;

    //过滤条件
    @Schema(description = "过滤条件")
    QueryParamEntity filter = QueryParamEntity.of();

}
