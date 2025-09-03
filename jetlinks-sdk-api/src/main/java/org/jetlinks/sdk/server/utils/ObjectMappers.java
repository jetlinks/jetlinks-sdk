package org.jetlinks.sdk.server.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

import java.io.InputStream;
import java.util.List;

public class ObjectMappers {

    public static final ObjectMapper JSON_MAPPER = org.jetlinks.core.utils.json.ObjectMappers.JSON_MAPPER;
    public static final ObjectMapper CBOR_MAPPER = org.jetlinks.core.utils.json.ObjectMappers.CBOR_MAPPER;
    public static final ObjectMapper SMILE_MAPPER = org.jetlinks.core.utils.json.ObjectMappers.SMILE_MAPPER;
    public static final XmlMapper XML_MAPPER = org.jetlinks.core.utils.json.ObjectMappers.XML_MAPPER;


    @SneakyThrows
    public static String toJsonString(Object data) {
        return org.jetlinks.core.utils.json.ObjectMappers.toJsonString(data);
    }

    @SneakyThrows
    public static byte[] toJsonBytes(Object data) {
        return org.jetlinks.core.utils.json.ObjectMappers.toJsonBytes(data);
    }

    @SneakyThrows
    public static <T> T parseJson(byte[] data, Class<T> type) {
        return org.jetlinks.core.utils.json.ObjectMappers.parseJson(data, type);
    }

    @SneakyThrows
    public static <T> T parseJson(InputStream data, Class<T> type) {
        return org.jetlinks.core.utils.json.ObjectMappers.parseJson(data, type);
    }

    @SneakyThrows
    public static <T> T parseJson(String data, Class<T> type) {
        return org.jetlinks.core.utils.json.ObjectMappers.parseJson(data, type);
    }

    @SneakyThrows
    public static <T> List<T> parseJsonArray(InputStream data, Class<T> type) {
        return org.jetlinks.core.utils.json.ObjectMappers.parseJsonArray(data, type);
    }

    @SneakyThrows
    public static <T> List<T> parseJsonArray(byte[] data, Class<T> type) {
        return org.jetlinks.core.utils.json.ObjectMappers.parseJsonArray(data, type);
    }

    @SneakyThrows
    public static <T> List<T> parseJsonArray(String data, Class<T> type) {
        return org.jetlinks.core.utils.json.ObjectMappers.parseJsonArray(data, type);
    }

    /**
     * 转换数据流为json对象流
     *
     * @param stream 数据流
     * @param type   json对象类型
     * @param <T>    json对象类型
     * @return json对象流
     */
    public static <T> Flux<T> parseJsonStream(Flux<DataBuffer> stream,
                                              Class<T> type) {
        return org.jetlinks.core.utils.json.ObjectMappers.parseJsonStream(stream, type);
    }

    /**
     * 转换数据流为json对象流
     *
     * @param stream 数据流
     * @param type   json对象类型
     * @param mapper json转换器
     * @param <T>    json对象类型
     * @return json对象流
     */
    public static <T> Flux<T> parseJsonStream(Flux<DataBuffer> stream,
                                              Class<T> type,
                                              ObjectMapper mapper) {
        return org.jetlinks.core.utils.json.ObjectMappers.parseJsonStream(stream, type, mapper);
    }

    /**
     * 从 JSON 数据流中提取指定字段的值流
     *
     * @param stream    数据流
     * @param fieldPath 字段路径，支持嵌套字段如 "data" 或 "result.items"
     * @param valueType 字段值类型
     * @param <T>       字段值类型
     * @return 字段值流
     */
    public static <T> Flux<T> parseJsonStreamField(Flux<DataBuffer> stream,
                                                   String fieldPath,
                                                   Class<T> valueType) {
        return org.jetlinks.core.utils.json.ObjectMappers.parseJsonStreamField(stream, fieldPath, valueType);
    }

    /**
     * 从 JSON 数据流中提取指定字段的值流
     *
     * @param stream    数据流
     * @param fieldPath 字段路径，支持嵌套字段如 "data" 或 "result.items"
     * @param valueType 字段值类型
     * @param mapper    json转换器
     * @param <T>       字段值类型
     * @return 字段值流
     */
    public static <T> Flux<T> parseJsonStreamField(Flux<DataBuffer> stream,
                                                   String fieldPath,
                                                   Class<T> valueType,
                                                   ObjectMapper mapper) {
        return org.jetlinks.core.utils.json.ObjectMappers.parseJsonStreamField(stream, fieldPath, valueType, mapper);
    }

    /**
     * 转换数据流为json字节流
     *
     * @param objectStream 数据流
     * @return json字节流
     */
    public static Flux<byte[]> toJsonStream(Flux<?> objectStream) {
        return org.jetlinks.core.utils.json.ObjectMappers.toJsonStream(objectStream);
    }

    /**
     * 转换数据流为json字节流
     *
     * @param objectStream 数据流
     * @param mapper       json转换器
     * @return json字节流
     */
    public static Flux<byte[]> toJsonStream(Flux<?> objectStream, ObjectMapper mapper) {
        return org.jetlinks.core.utils.json.ObjectMappers.toJsonStream(objectStream,mapper);
    }

}
