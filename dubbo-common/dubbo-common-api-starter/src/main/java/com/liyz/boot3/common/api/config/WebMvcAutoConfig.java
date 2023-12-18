package com.liyz.boot3.common.api.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.liyz.boot3.common.api.advice.GlobalControllerExceptionAdvice;
import com.liyz.boot3.common.api.error.ErrorApiController;
import com.liyz.boot3.common.api.util.I18nMessageUtil;
import com.liyz.boot3.common.util.DateUtil;
import com.liyz.boot3.common.util.deserializer.TrimDeserializer;
import com.liyz.boot3.common.util.serializer.DesensitizationSerializer;
import com.liyz.boot3.common.util.serializer.DoubleSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

/**
 * Desc:mvc auto config
 * 这里注意使用{@link WebMvcConfigurer}与{@link WebMvcConfigurationSupport}区别
 * 主要区别在于{@link org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration}中的
 * condition {ConditionalOnMissingBean({WebMvcConfigurationSupport.class})}，使用support则springboot原生的config则不会
 * 创建，并且在{@link org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.EnableWebMvcConfiguration}
 * 的父类{@link DelegatingWebMvcConfiguration}中会找出所有的{@link WebMvcConfigurer}进行逐步配置
 *
 * 注: 所以建议大家使用{@link WebMvcConfigurer}来或者自己的mvc配置
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/18 9:45
 */
@Configuration
@EnableConfigurationProperties({ServerProperties.class})
@AutoConfigureOrder(value = Ordered.HIGHEST_PRECEDENCE)
public class WebMvcAutoConfig implements WebMvcConfigurer {

    @Bean
    public GlobalControllerExceptionAdvice globalControllerExceptionAdvice() {
        return new GlobalControllerExceptionAdvice();
    }

    @Bean
    public ErrorApiController errorApiController(ServerProperties serverProperties) {
        return new ErrorApiController(serverProperties);
    }

    @Bean
    public I18nMessageUtil i18nMessageUtil() {
        return new I18nMessageUtil();
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver("locale");
        cookieLocaleResolver.setCookieMaxAge(Duration.ofDays(1));
        return cookieLocaleResolver;
    }

    /**
     * 扩展json
     *
     * @param converters http消息协商列表
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (CollectionUtils.isEmpty(converters)) {
            return;
        }
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

    /**
     * 设置跨域
     * 如无需要，请勿设置跨域
     *
     * @param registry 跨域注册器
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("**/current").allowedMethods("GET").allowedHeaders("Authorization");
    }

    /**
     * 增加一个国际化拦截器
     *
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
    }
}
