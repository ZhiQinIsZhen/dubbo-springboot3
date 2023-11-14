package com.liyz.boot3.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.liyz.boot3.common.util.serializer.DoubleSerializer;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.TimeZone;

/**
 * Desc:Json tool
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/8 15:12
 */
@UtilityClass
public class JsonMapperUtil {

    private static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder
            .json()
            .createXmlMapper(false)
            .dateFormat(new SimpleDateFormat(DateUtil.PATTERN_DATE_TIME))
            .timeZone(TimeZone.getTimeZone(DateUtil.TIME_ZONE_GMT8))
            .build()
            .enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false)
            .registerModule(new SimpleModule()
                    .addSerializer(Long.class, ToStringSerializer.instance)
                    .addSerializer(Long.TYPE, ToStringSerializer.instance)
                    .addSerializer(Double.class, new DoubleSerializer())
                    .addSerializer(Double.TYPE, new DoubleSerializer())
            );

    private static final ObjectWriter OBJECT_WRITER = OBJECT_MAPPER.writerWithDefaultPrettyPrinter();

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    @SneakyThrows
    public static String toJSONString(Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    @SneakyThrows
    public static String toJSONPrettyString(Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        return OBJECT_WRITER.writeValueAsString(obj);
    }

    @SneakyThrows
    public static <T> T readValue(String content, Class<T> clazz) {
        if (StringUtils.isBlank(content) || Objects.isNull(clazz)) {
            return null;
        }
        return OBJECT_MAPPER.readValue(content, clazz);
    }

    @SneakyThrows
    public static <T> T readValue(InputStream inputStream, Class<T> clazz) {
        if (ObjectUtils.anyNull(inputStream, clazz)) {
            return null;
        }
        return OBJECT_MAPPER.readValue(inputStream, clazz);
    }

    @SneakyThrows
    public static <T> T readValue(JsonNode jsonNode, Class<T> clazz) {
        if (ObjectUtils.anyNull(jsonNode, clazz)) {
            return null;
        }
        return OBJECT_MAPPER.treeToValue(jsonNode, clazz);
    }

    @SneakyThrows
    public static void writeValue(OutputStream out, Object value) {
        OBJECT_MAPPER.writeValue(out, value);
    }

    @SneakyThrows
    public static JsonNode readTree(Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return OBJECT_MAPPER.readTree((String) obj);
        }
        return OBJECT_MAPPER.readTree(OBJECT_MAPPER.writeValueAsString(obj));
    }
}
