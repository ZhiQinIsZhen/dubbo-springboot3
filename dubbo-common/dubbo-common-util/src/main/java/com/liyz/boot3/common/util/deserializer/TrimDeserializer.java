package com.liyz.boot3.common.util.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.liyz.boot3.common.util.annotation.Trim;

import java.io.IOException;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/4 13:13
 */
public class TrimDeserializer extends JsonDeserializer<String> implements ContextualDeserializer {

    public TrimDeserializer() {
    }

    public TrimDeserializer(Trim annotation) {
        this.annotation = annotation;
    }

    private Trim annotation;

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return Objects.nonNull(annotation) && Objects.nonNull(jsonParser.getText()) ? jsonParser.getText().trim() : jsonParser.getText();
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        if (Objects.isNull(beanProperty)) {
            return this;
        }
        Trim trim = beanProperty.getAnnotation(Trim.class);
        return Objects.isNull(trim) ? this : new TrimDeserializer(trim);
    }
}
