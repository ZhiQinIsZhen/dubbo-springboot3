package com.liyz.boot3.common.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.liyz.boot3.common.util.DesensitizeUtil;
import com.liyz.boot3.common.util.annotation.Desensitization;

import java.io.IOException;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/4 15:21
 */
public class DesensitizationSerializer extends JsonSerializer<String> implements ContextualSerializer {

    public DesensitizationSerializer() {
    }

    public DesensitizationSerializer(Desensitization annotation) {
        this.annotation = annotation;
    }

    private Desensitization annotation;

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.isNull(annotation)) {
            jsonGenerator.writeString(s);
        } else {
            jsonGenerator.writeString(DesensitizeUtil.getService(annotation.value()).desensitize(s, annotation));
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (Objects.isNull(beanProperty)) {
            return this;
        }
        Desensitization desensitization = beanProperty.getAnnotation(Desensitization.class);
        return Objects.isNull(desensitization) ? this : new DesensitizationSerializer(desensitization);
    }
}
