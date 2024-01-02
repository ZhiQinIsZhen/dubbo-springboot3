package com.liyz.boot3.common.search.mapper;

import com.liyz.boot3.common.util.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.Arrays;
import java.util.Set;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/30 17:58
 */
@Slf4j
public class ClassPathEsMapperScanner extends ClassPathBeanDefinitionScanner {

    private final Class<? extends EsMapperFactoryBean> mapperFactoryBeanClass = EsMapperFactoryBean.class;

    public ClassPathEsMapperScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void registerFilters() {
        this.addIncludeFilter(new AssignableTypeFilter(EsMapper.class) {
            protected boolean matchClassName(String className) {
                return false;
            }
        });
        this.addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            log.warn("No ElasticSearch mapper was found in '{}' package. Please check your configuration.", Arrays.toString(basePackages));
        } else {
            this.processBeanDefinitions(beanDefinitions);
        }
        return beanDefinitions;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        for (BeanDefinitionHolder holder : beanDefinitions) {
            AbstractBeanDefinition definition = (AbstractBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            log.info("Creating MapperFactoryBean with name '{}'  and '{}'  mapperInterface", holder.getBeanName(), beanClassName);
            try {
                Class<?> beanClass = ResourceUtil.classForName(beanClassName);
                definition.getConstructorArgumentValues().addGenericArgumentValue(beanClass);
            } catch (ClassNotFoundException e) {
                log.error("Class not fund, className '{}'", beanClassName);
            }
            definition.setBeanClass(this.mapperFactoryBeanClass);
        }
    }
}
