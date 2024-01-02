package com.liyz.boot3.common.search.config;

import com.liyz.boot3.common.search.annotation.EsMapperScan;
import com.liyz.boot3.common.search.mapper.EsMapperScannerConfig;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/30 15:07
 */
public class EsMapperScannerRegistrar implements ImportBeanDefinitionRegistrar {

    private static final String BASE_PACKAGES = "basePackages";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EsMapperScan.class.getName()));
        if (mapperScanAttrs != null) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(EsMapperScannerConfig.class);
            List<String> basePackages = Arrays.stream(mapperScanAttrs.getStringArray(BASE_PACKAGES)).filter(StringUtils::hasText).collect(Collectors.toList());
            if (basePackages.isEmpty()) {
                basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
            }
            builder.addPropertyValue(BASE_PACKAGES, StringUtils.collectionToCommaDelimitedString(basePackages));
            builder.setRole(2);
            registry.registerBeanDefinition(generateBaseBeanName(importingClassMetadata, 0),
                    builder.getBeanDefinition());
        }
    }

    private static String generateBaseBeanName(AnnotationMetadata importingClassMetadata, int index) {
        String var10000 = importingClassMetadata.getClassName();
        return var10000 + "#" + EsMapperScannerRegistrar.class.getSimpleName() + "#" + index;
    }
}
