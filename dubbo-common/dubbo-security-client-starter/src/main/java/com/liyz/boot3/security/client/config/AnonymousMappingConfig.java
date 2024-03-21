package com.liyz.boot3.security.client.config;

import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.security.client.annotation.Anonymous;
import com.liyz.boot3.security.client.constant.SecurityClientConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
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
     *  获取免鉴权的mappings
     */
    private static final Set<String> anonymousMappings = new HashSet<>(100);
    /**
     * 额外的mappings
     */
    private static final Set<String> anonymousExtraMappings = new HashSet<>(100);
    /**
     * 需要鉴权mappings
     */
    private static final Set<String> authExtraMappings = new HashSet<>(100);

    /**
     * 是否匹配路劲
     *
     * @param path 路劲
     * @return bool
     */
    public static boolean pathMatch(String path) {
        if (anonymousMappings.contains(path) || anonymousExtraMappings.contains(path)) {
            return true;
        }
        if (authExtraMappings.contains(path)) {
            return false;
        }
        //swagger
        for (String mapping : SecurityClientConstant.KNIFE4J_IGNORE_RESOURCES) {
            if (new AntPathMatcher().match(mapping, path)) {
                anonymousExtraMappings.add(path);
                return true;
            }
        }
        //actuator
        for (String mapping : SecurityClientConstant.ACTUATOR_RESOURCES) {
            if (new AntPathMatcher().match(mapping, path)) {
                anonymousExtraMappings.add(path);
                return true;
            }
        }
        authExtraMappings.add(path);
        return false;
    }

    /**
     * 获取免鉴权的mappings
     *
     * @return mappings
     */
    public static String[] getAnonymousMappings() {
        return anonymousMappings.toArray(new String[0]);
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, RequestMappingHandlerMapping> map = applicationContext.getBeansOfType(RequestMappingHandlerMapping.class);
        if (!CollectionUtils.isEmpty(map)) {
            map.values().forEach(handlerMapping -> handlerMapping.getHandlerMethods().forEach((k, v) -> {
                if (v.getBeanType().isAnnotationPresent(Anonymous.class) || (v.hasMethodAnnotation(Anonymous.class))) {
                    if (Objects.nonNull(k.getPathPatternsCondition())) {
                        anonymousMappings.addAll(k.getPathPatternsCondition().getPatternValues());
                    }
                }
            }));
        }
        log.warn("Anonymous mappings : {}", JsonMapperUtil.toJSONString(anonymousMappings));
    }
}
