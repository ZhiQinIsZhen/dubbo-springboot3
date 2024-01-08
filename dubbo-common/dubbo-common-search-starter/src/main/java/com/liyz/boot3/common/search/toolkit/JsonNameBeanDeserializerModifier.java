package com.liyz.boot3.common.search.toolkit;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/8 14:39
 */
public class JsonNameBeanDeserializerModifier extends BeanDeserializerModifier {

    @Override
    public List<BeanPropertyDefinition> updateProperties(DeserializationConfig config, BeanDescription beanDesc, List<BeanPropertyDefinition> propDefs) {
        if (CollectionUtils.isEmpty(propDefs)) {
            return propDefs;
        }
        List<BeanPropertyDefinition> result = new ArrayList<>(propDefs.size());
        for (BeanPropertyDefinition definition : propDefs) {
            Field field = definition.getField().getAnnotation(Field.class);
            if (field != null && StringUtils.isNotBlank(field.name()) && !definition.getName().equals(field.name())) {
                result.add(definition.withName(PropertyName.construct(field.name())));
            } else {
                result.add(definition);
            }
        }
        return result;
    }
}
