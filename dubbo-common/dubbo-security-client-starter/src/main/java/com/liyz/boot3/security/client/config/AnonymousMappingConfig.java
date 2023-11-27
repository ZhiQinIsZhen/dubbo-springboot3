package com.liyz.boot3.security.client.config;

import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.security.client.annotation.Anonymous;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Desc:anonymous mapping
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:30
 */
@Slf4j
@Configuration
public class AnonymousMappingConfig implements ApplicationContextAware, InitializingBean {

    /**
     * -- GETTER --
     *  获取免鉴权的mappings
     *  can see annotation
     *
     * @return 免鉴权的mappings
     */
    @Getter
    private static final Set<String> anonymousMappings = new HashSet<>(100);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, RequestMappingHandlerMapping> map = applicationContext.getBeansOfType(RequestMappingHandlerMapping.class);
        if (!CollectionUtils.isEmpty(map)) {
            map.values().forEach(handlerMapping -> {
                handlerMapping.getHandlerMethods().forEach((k, v) -> {
                    if (v.getBeanType().isAnnotationPresent(Anonymous.class) || (v.hasMethodAnnotation(Anonymous.class))) {
                        if (Objects.nonNull(k.getPathPatternsCondition())) {
                            anonymousMappings.addAll(k.getPathPatternsCondition().getPatternValues());
                        }
                    }
                });
            });
        }
        log.info("Anonymous mappings : {}", JsonMapperUtil.toJSONString(anonymousMappings));
    }

}
