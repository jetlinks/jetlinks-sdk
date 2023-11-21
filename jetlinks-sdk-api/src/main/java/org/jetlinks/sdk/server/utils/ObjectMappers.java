package org.jetlinks.sdk.server.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.unit.DataSize;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ObjectMappers {

    public static final ObjectMapper JSON_MAPPER;

    static {
        JSON_MAPPER = Jackson2ObjectMapperBuilder
            .json()
            .build()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);


    }


    @SneakyThrows
    public static String toJsonString(Object data) {
        return JSON_MAPPER.writeValueAsString(data);
    }

    @SneakyThrows
    public static byte[] toJsonBytes(Object data) {
        return JSON_MAPPER.writeValueAsBytes(data);
    }

    @SneakyThrows
    public static <T> T parseJson(byte[] data, Class<T> type) {
        return JSON_MAPPER.readValue(data, type);
    }

    @SneakyThrows
    public static <T> T parseJson(InputStream data, Class<T> type) {
        return JSON_MAPPER.readValue(data, type);
    }

    @SneakyThrows
    public static <T> T parseJson(String data, Class<T> type) {
        return JSON_MAPPER.readValue(data, type);
    }

    @SneakyThrows
    public static <T> List<T> parseJsonArray(InputStream data, Class<T> type) {
        return JSON_MAPPER.readerForListOf(type).readValue(data);
    }

    @SneakyThrows
    public static <T> List<T> parseJsonArray(byte[] data, Class<T> type) {
        return JSON_MAPPER.readerForListOf(type).readValue(data);
    }

    @SneakyThrows
    public static <T> T parseJsonArray(String data, Class<T> type) {
        return JSON_MAPPER.readerForListOf(type).readValue(data);
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
        return parseJsonStream(stream, type, JSON_MAPPER);
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
        Jackson2JsonDecoder decoder = new Jackson2JsonDecoder(mapper);
        decoder.setMaxInMemorySize((int) DataSize.ofMegabytes(8).toBytes());
        return decoder
            .decode(stream, ResolvableType.forType(type), null, null)
            .cast(type);
    }


    @SneakyThrows
    private static JsonGenerator createJsonGenerator(ObjectMapper mapper, OutputStream stream) {
        return mapper.createGenerator(stream);
    }

    @SneakyThrows
    private static void writeObject(JsonGenerator generator, Object data) {
        generator.writePOJO(data);
    }

    private static void safeClose(JsonGenerator closeable) {
        if (closeable.isClosed()) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException ignore) {

        }
    }
}
