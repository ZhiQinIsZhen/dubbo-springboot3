package com.liyz.boot3.common.es.mapper;

import com.liyz.boot3.common.es.io.Resources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.scope.ScopedProxyFactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/25 17:01
 */
@Slf4j
public class ClassPathEsMapperScanner extends ClassPathBeanDefinitionScanner {

    private Class<?> markerInterface;
    private Class<? extends EsMapperFactoryBean> mapperFactoryBeanClass = EsMapperFactoryBean.class;

    public ClassPathEsMapperScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void setMarkerInterface(Class<?> markerInterface) {
        this.markerInterface = markerInterface;
    }

    public void setMapperFactoryBeanClass(Class<? extends EsMapperFactoryBean> mapperFactoryBeanClass) {
        this.mapperFactoryBeanClass = mapperFactoryBeanClass == null ? EsMapperFactoryBean.class : mapperFactoryBeanClass;
    }

    public void registerFilters() {
        boolean acceptAllInterfaces = true;
        if (this.markerInterface != null) {
            this.addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
                protected boolean matchClassName(String className) {
                    return false;
                }
            });
            acceptAllInterfaces = false;
        }
        if (acceptAllInterfaces) {
            this.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        }
        this.addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            log.warn("No MyBatis mapper was found in '{}' package. Please check your configuration.", Arrays.toString(basePackages));
        } else {
            this.processBeanDefinitions(beanDefinitions);
        }
        return beanDefinitions;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        BeanDefinitionRegistry registry = this.getRegistry();
        beanDefinitions.forEach(holder -> {
            AbstractBeanDefinition definition = (AbstractBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            log.info("Creating MapperFactoryBean with name '{}'  and '{}'  mapperInterface", holder.getBeanName(), beanClassName);
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
            try {
                definition.getPropertyValues().add("mapperInterface", Resources.classForName(beanClassName));
            } catch (ClassNotFoundException var10) {
                log.error("错误", var10);
            }
            definition.setBeanClass(this.mapperFactoryBeanClass);
            definition.setAttribute("factoryBeanObjectType", beanClassName);
        });
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    @Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            log.warn("Skipping MapperFactoryBean with name '{}' and '{}' mapperInterface. Bean already defined with the same name!"
                    , beanName, beanDefinition.getBeanClassName());
            return false;
        }
    }
}
