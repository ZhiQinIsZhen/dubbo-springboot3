package com.liyz.boot3.common.api.config;

import com.liyz.boot3.common.api.advice.GlobalControllerExceptionAdvice;
import com.liyz.boot3.common.api.error.ErrorApiController;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

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
}
