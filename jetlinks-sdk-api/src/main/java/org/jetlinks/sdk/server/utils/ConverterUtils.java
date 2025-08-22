package org.jetlinks.sdk.server.utils;

import com.fasterxml.jackson.databind.ObjectReader;
import io.netty.buffer.ByteBuf;
import lombok.SneakyThrows;
import org.hswebframework.ezorm.core.param.Term;
import org.hswebframework.web.api.crud.entity.TermExpressionParser;
import org.hswebframework.web.bean.FastBeanCopier;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConverterUtils {

    /**
     * 尝试转换值为集合,如果不是集合格式则直接返回该值
     *
     * @param value     值
     * @param converter 转换器,用户转换单个结果
     * @return 转换结果
     */
    public static Object tryConvertToList(Object value, Function<Object, Object> converter) {
        return org.jetlinks.core.utils.ConverterUtils.tryConvertToList(value, converter);
    }

    /**
     * 转换参数为指定类型的List
     *
     * @param value     参数
     * @param converter 类型转换器
     * @param <T>       List中元素类型
     * @return 转换后的List
     */
    public static <T> List<T> convertToList(Object value, Function<Object, T> converter) {
        return org.jetlinks.core.utils.ConverterUtils.convertToList(value, converter);
    }

    /**
     * 转换参数为List
     *
     * @param value 参数
     * @return 排序后的流
     */
    public static List<Object> convertToList(Object value) {
        return convertToList(value, Function.identity());
    }


    /**
     * 将Map转为tag,如果map中到值不是数字,则转为json.
     * <pre>
     *      {"key1":"value1","key2":["value2"]} => key,value1,key2,["value2"]
     *  </pre>
     *
     * @param map map
     * @return tags
     */
    @SneakyThrows
    public static String[] convertMapToTags(Map<String, Object> map) {
        return org.jetlinks.core.utils.ConverterUtils.convertMapToTags(map);
    }

    static final ObjectReader termReader = ObjectMappers.JSON_MAPPER.readerForListOf(Term.class);

    /**
     * 将对象转为查询条件,支持json和表达式格式,如:
     * <pre>
     *   //name = xxx and age > 10
     *   convertTerms("name is xxx and age gt 10")
     *
     *   convertTerms({"name":"xxx","age$gt":10})
     * </pre>
     *
     * @param value
     * @return 条件集合
     */
    @SuppressWarnings("all")
    @SneakyThrows
    public static List<Term> convertTerms(Object value) {
        if (value instanceof String) {
            String strVal = String.valueOf(value);
            //json字符串
            if (strVal.startsWith("[")) {
                return termReader.readValue(strVal);
            } else if (strVal.startsWith("{")) {
                return TermExpressionParser.parse(ObjectMappers.parseJson(strVal, Map.class));
            } else {
                //表达式
                return TermExpressionParser.parse(strVal);
            }
        }
        if (value instanceof List) {
            return ((List<?>) value)
                .stream()
                .map(obj -> obj instanceof Term ? ((Term) obj) : FastBeanCopier.copy(obj, new Term()))
                .collect(Collectors.toList());
        } else if (value instanceof Map) {
            return TermExpressionParser.parse(((Map<String, Object>) value));
        } else {
            throw new UnsupportedOperationException("unsupported term value:" + value);
        }
    }

    public static DataBuffer convertDataBuffer(Object obj) {
        return org.jetlinks.core.utils.ConverterUtils.convertDataBuffer(obj);
    }

    public static <T> ByteBuf convertNettyBuffer(T obj,
                                                 Function<T, ByteBuf> fallback) {
        return org.jetlinks.core.utils.ConverterUtils.convertNettyBuffer(obj, fallback);
    }

    public static ByteBuf convertNettyBuffer(Object obj) {
        return org.jetlinks.core.utils.ConverterUtils.convertNettyBuffer(obj);
    }


    @SuppressWarnings("all")
    public static HttpHeaders convertHttpHeaders(Object headers) {
        return org.jetlinks.core.utils.ConverterUtils.convertHttpHeaders(headers);
    }


}
