package org.jetlinks.sdk.server.utils;

import com.fasterxml.jackson.databind.ObjectReader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import lombok.SneakyThrows;
import org.apache.commons.collections4.MapUtils;
import org.hswebframework.ezorm.core.param.Term;
import org.hswebframework.web.api.crud.entity.TermExpressionParser;
import org.hswebframework.web.bean.FastBeanCopier;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Array;
import java.nio.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
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
        List<Object> list = convertToList(value, converter);
        if (list.size() == 1) {
            return converter.apply(list.get(0));
        }
        return list;
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
        if (value == null) {
            return Collections.emptyList();
        }

        if (value instanceof String) {
            String[] arr = ((String) value).split(",");
            if (arr.length == 1) {
                return Collections.singletonList(converter.apply(arr[0]));
            }
            List<T> list = new ArrayList<>(arr.length);
            for (String s : arr) {
                list.add(converter.apply(s));
            }
            return list;
        }

        if (value instanceof Collection) {
            List<T> list = new ArrayList<>(((Collection<?>) value).size());
            for (Object o : ((Collection<?>) value)) {
                list.add(converter.apply(o));
            }
            return list;
        }

        if (value.getClass().isArray()) {
            int len = Array.getLength(value);
            List<T> list = new ArrayList<>(len);
            for (int i = 0; i < len; i++) {
                list.add(converter.apply(Array.get(value, i)));
            }
            return list;
        }

        return Collections.singletonList(converter.apply(value));
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
        if (MapUtils.isEmpty(map)) {
            return new String[0];
        }
        String[] tags = new String[map.size() * 2];
        int index = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            String strValue = value instanceof String
                ? String.valueOf(value)
                : ObjectMappers.JSON_MAPPER.writeValueAsString(value);

            tags[index++] = key;
            tags[index++] = strValue;
        }
        if (tags.length > index) {
            return Arrays.copyOf(tags, index);
        }
        return tags;
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

    private static final NettyDataBufferFactory factory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);

    public static DataBuffer convertDataBuffer(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof DataBuffer) {
            return ((DataBuffer) obj);
        }
        return factory.wrap(convertNettyBuffer(obj));
    }

    public static <T> ByteBuf convertNettyBuffer(T obj,
                                                 Function<T, ByteBuf> fallback) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof ByteBuf) {
            return ((ByteBuf) obj);
        }

        if (obj instanceof byte[]) {
            return Unpooled.wrappedBuffer(((byte[]) obj));
        }

        if (obj instanceof NettyDataBuffer) {
            return ((NettyDataBuffer) obj).getNativeBuffer();
        }

        if (obj instanceof DataBuffer) {
            return Unpooled.wrappedBuffer(((DataBuffer) obj).asByteBuffer());
        }

//        if (obj instanceof ByteBuffer) {
//            return Unpooled.wrappedBuffer(((ByteBuffer) obj));
//        }
        if (obj instanceof Buffer) {
            return convertNioBufferToNettyBuf(((Buffer) obj),
                                              ignore -> {
                                              });
        }

        if (obj instanceof String) {
            String str = String.valueOf(obj);
            // hex
            if (str.startsWith("0x")) {
                return Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(str, 2, str.length() - 2));
            }
            //data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA
            if (str.startsWith("data:")) {
                return Unpooled.wrappedBuffer(
                    Base64
                        .getDecoder()
                        .decode(str.substring(str.indexOf(",") + 1)));
            }
            // base64
            byte[] strBytes = str.getBytes();
            if (org.apache.commons.codec.binary.Base64.isBase64(strBytes)) {
                try {
                    return Unpooled.wrappedBuffer(
                        Base64
                            .getDecoder()
                            .decode(strBytes));
                } catch (Throwable ignore) {
                }
            }
            return Unpooled.wrappedBuffer(strBytes);
        }

        return fallback.apply(obj);
    }

    public static ByteBuf convertNettyBuffer(Object obj) {
        return convertNettyBuffer(obj, val -> Unpooled.wrappedBuffer(String.valueOf(val).getBytes()));
    }

    private static <T extends Buffer> ByteBuf convertNioBufferToNettyBuf0(T nioBuffer,
                                                                          int bit,
                                                                          Consumer<ByteBuffer> init,
                                                                          BiConsumer<ByteBuffer, T> consumer) {
        nioBuffer.mark();

        ByteBuffer buffer = nioBuffer.isDirect()
            ? ByteBuffer.allocateDirect(nioBuffer.remaining() * bit)
            : ByteBuffer.allocate(nioBuffer.remaining() * bit);

        init.accept(buffer);

        consumer.accept(buffer, nioBuffer);

        nioBuffer.reset();

        return Unpooled.wrappedBuffer(buffer);
    }

    public static ByteBuf convertNioBufferToNettyBuf(Buffer buffer, Consumer<ByteBuffer> init) {
        if (buffer instanceof ByteBuffer) {
            init.accept((ByteBuffer) buffer);
            return Unpooled.wrappedBuffer((ByteBuffer) buffer);
        }

        if (buffer instanceof ShortBuffer) {

            return convertNioBufferToNettyBuf0(
                (ShortBuffer) buffer,
                2,
                init,
                (buf, nioBuffer) -> buf.asShortBuffer().put(nioBuffer));
        }

        if (buffer instanceof IntBuffer) {
            return convertNioBufferToNettyBuf0(
                (IntBuffer) buffer,
                4,
                init,
                (buf, nioBuffer) -> buf.asIntBuffer().put(nioBuffer));
        }

        if (buffer instanceof LongBuffer) {
            return convertNioBufferToNettyBuf0(
                (LongBuffer) buffer,
                8,
                init,
                (buf, nioBuffer) -> buf.asLongBuffer().put(nioBuffer));
        }

        if (buffer instanceof FloatBuffer) {
            return convertNioBufferToNettyBuf0(
                (FloatBuffer) buffer,
                4,
                init,
                (buf, nioBuffer) -> buf.asFloatBuffer().put(nioBuffer));
        }

        if (buffer instanceof DoubleBuffer) {
            return convertNioBufferToNettyBuf0(
                (DoubleBuffer) buffer,
                8,
                init,
                (buf, nioBuffer) -> buf.asDoubleBuffer().put(nioBuffer));
        }

        if (buffer instanceof CharBuffer) {
            return convertNioBufferToNettyBuf0(
                (CharBuffer) buffer,
                2,
                init,
                (buf, nioBuffer) -> buf.asCharBuffer().put(nioBuffer));
        }


        throw new UnsupportedOperationException("unsupported buffer type:" + buffer.getClass());
    }

    @SuppressWarnings("all")
    public static HttpHeaders convertHttpHeaders(Object headers) {
        if (headers instanceof HttpHeaders) {
            return (HttpHeaders) headers;
        }
        if (headers instanceof MultiValueMap) {
            return new HttpHeaders((MultiValueMap) headers);
        }
        if (headers instanceof Map<?, ?>) {
            Map<?, ?> httpHeaders = (Map<?, ?>) headers;
            HttpHeaders newHeader = new HttpHeaders();
            for (Map.Entry<?, ?> entry : httpHeaders.entrySet()) {
                newHeader.put(String.valueOf(entry.getKey()),
                              convertToList(entry.getValue(), String::valueOf));
            }
            return newHeader;
        }
        return org.jetlinks.core.utils.ConverterUtils.convert(headers,HttpHeaders.class);
    }



}
