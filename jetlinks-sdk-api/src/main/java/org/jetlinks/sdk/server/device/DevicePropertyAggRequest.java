package org.jetlinks.sdk.server.device;

/**
 * 设备属性聚合查询请求.
 *
 * @author zhangji 2024/1/17
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.hswebframework.web.validator.ValidatorUtils;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 设备属性聚合查询请求.
 *
 * @author zhangji 2024/1/17
 */
@Getter
@Setter
public class DevicePropertyAggRequest {

    private List<DevicePropertyAggregation> columns;

    private AggregationRequest query;

    public void validate() {
        Assert.notNull(columns, "columns can not be null");
        Assert.notNull(query, "query can not be null");
        Assert.notEmpty(columns, "columns can not be empty");
        columns.forEach(DevicePropertyAggregation::validate);
    }

    @Getter
    @Setter
    public static class DevicePropertyAggregation {
        @Schema(description = "属性ID")
        @NotBlank
        private String property;

        @Schema(description = "别名,默认和property一致")
        private String alias;

        @Schema(description = "聚合方式,支持(count,sum,max,min,avg)", type = "string")
        @NotNull
        private String agg;

        public void validate() {
            ValidatorUtils.tryValidate(this);
        }
    }

    @Getter
    @Setter
    public static class AggregationRequest {
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
        int limit = 30;

        //过滤条件
        @Schema(description = "过滤条件")
        QueryParamEntity filter = QueryParamEntity.of();

    }

}
