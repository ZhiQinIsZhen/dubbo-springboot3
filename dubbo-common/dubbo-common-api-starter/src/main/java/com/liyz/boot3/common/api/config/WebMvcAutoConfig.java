package com.liyz.boot3.common.api.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.liyz.boot3.common.api.advice.GlobalControllerExceptionAdvice;
import com.liyz.boot3.common.api.error.ErrorApiController;
import com.liyz.boot3.common.util.DateUtil;
import com.liyz.boot3.common.util.deserializer.TrimDeserializer;
import com.liyz.boot3.common.util.serializer.DesensitizationSerializer;
import com.liyz.boot3.common.util.serializer.DoubleSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

/**
 * Desc:mvc auto config
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 11:13
 */
@Configuration
@AutoConfigureOrder(value = Ordered.HIGHEST_PRECEDENCE)
public class WebMvcAutoConfig extends WebMvcConfigurationSupport {

    @Bean
    public GlobalControllerExceptionAdvice globalControllerExceptionAdvice() {
        return new GlobalControllerExceptionAdvice();
    }

    @Bean
    public ErrorApiController errorApiController(ServerProperties serverProperties) {
        return new ErrorApiController(serverProperties);
    }

    /**
     * 允许加载本地静态资源
     *
     * @param registry 资源注册器
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.extendMessageConverters(converters);
        if (!CollectionUtils.isEmpty(converters)) {
            Optional<HttpMessageConverter<?>> optional = converters
                    .stream()
                    .filter(item -> item instanceof MappingJackson2HttpMessageConverter)
                    .findFirst();
            optional.ifPresent(item -> {
                MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) item;
                ObjectMapper objectMapper = converter.getObjectMapper();
                SimpleModule simpleModule = new SimpleModule();
                simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
                simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
                simpleModule.addSerializer(Double.class, new DoubleSerializer());
                simpleModule.addSerializer(Double.TYPE, new DoubleSerializer());
                simpleModule.addSerializer(String.class, new DesensitizationSerializer());
                simpleModule.addDeserializer(String.class, new TrimDeserializer());
                objectMapper.setDateFormat(new SimpleDateFormat(DateUtil.PATTERN_DATE_TIME));
                objectMapper.setTimeZone(TimeZone.getTimeZone(DateUtil.TIME_ZONE_GMT8));
                objectMapper.registerModule(simpleModule);
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
                objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
            });
        }
    }

    /**
     * 设置跨域
     * 如无需要，请勿设置跨域
     *
     * @param registry 跨域注册器
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("**/current").allowedMethods("GET").allowedHeaders("Authorization");
    }
}
