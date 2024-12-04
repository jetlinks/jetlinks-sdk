package org.jetlinks.sdk.server.commons.cmd;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.core.dsl.Query;
import org.hswebframework.ezorm.core.param.QueryParam;
import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.hswebframework.web.bean.FastBeanCopier;
import org.jetlinks.core.command.AbstractConvertCommand;
import org.jetlinks.core.metadata.DataType;
import org.jetlinks.core.metadata.SimplePropertyMetadata;
import org.jetlinks.core.metadata.types.ArrayType;
import org.jetlinks.core.metadata.types.ObjectType;
import org.jetlinks.core.metadata.types.StringType;
import org.jetlinks.sdk.server.commons.cmd.metadata.QueryParamSpec;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 查询命令,用于实现动态查询相关操作
 * <pre>{@code
 *
 * {
 *     "terms":[
 *          {
 *              "column":"name",
 *              "termType":"like",
 *              "value":"zhang%"
 *          }
 *     ]
 * }
 *
 * }</pre>
 *
 * @author zhouhao
 * @since 2.1
 */
public abstract class QueryCommand<T, Self extends QueryCommand<T, Self>> extends AbstractConvertCommand<T, Self> {

    private transient QueryParamEntity queryParam;

    /**
     * 使用DSL方式来构造动态查询条件
     *
     * @param consumer DSL构造器
     * @return Self
     */
    public Self dsl(Consumer<Query<?, QueryParamEntity>> consumer) {
        Query<?, QueryParamEntity> param = QueryParamEntity.newQuery();
        consumer.accept(param);
        return withQueryParam(param.getParam());
    }

    /**
     * 设置动态查询条件
     *
     * @param queryParam 动态查询条件
     * @return Self
     */
    public Self withQueryParam(QueryParamEntity queryParam) {
        this.queryParam = queryParam;

       // FastBeanCopier.copy(queryParam, writable());
        return castSelf();
    }

    @Override
    public Map<String, Object> readable() {
        Map<String, Object> prop = super.readable();
        if (prop.isEmpty() && this.queryParam != null) {
            FastBeanCopier.copy(this.queryParam, writable());
        }
        return super.readable();
    }

    /**
     * 获取动态查询条件
     *
     * @return 动态查询条件
     */
    public QueryParamEntity asQueryParam() {
        if (null != queryParam) {
            return queryParam;
        }
        return queryParam = FastBeanCopier.copy(readable(), new QueryParamEntity());
    }

    public static SimplePropertyMetadata getTermsMetadata() {
        return SimplePropertyMetadata.of("terms", "动态条件", new ArrayType()
            .elementType(getTermsDataType()));
    }

    public static DataType getTermsDataType() {
        return new ObjectType()
            .addProperty("column", "列名(属性名)", StringType.GLOBAL)
            .addProperty("termType", "条件类型,如:like,gt,lt", StringType.GLOBAL)
            .addProperty("value", "条件值", StringType.GLOBAL);
    }

    @Override
    @SuppressWarnings("all")
    public <T1> T1 as(Type type) {
        if (type == QueryParamEntity.class || type == QueryParam.class) {
            return (T1) asQueryParam();
        }
        return super.as(type);
    }

    @Getter
    @Setter
    protected static abstract class InputSpec extends QueryParamSpec {

    }


}
