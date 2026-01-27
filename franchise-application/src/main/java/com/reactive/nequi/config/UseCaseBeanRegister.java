package com.reactive.nequi.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Component;

import java.beans.Introspector;
import java.util.regex.Pattern;

@Component
public class UseCaseBeanRegister implements BeanDefinitionRegistryPostProcessor {

    private static final String BASE_PACKAGE = "com.reactive.nequi.usecases";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*UseCase$")));

        for (BeanDefinition bd : scanner.findCandidateComponents(BASE_PACKAGE)) {

            try {
                Class<?> useCaseClass = Class.forName(bd.getBeanClassName());

                RootBeanDefinition beanDefinition = new RootBeanDefinition(useCaseClass);

                beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);

                String beanName = Introspector.decapitalize(useCaseClass.getSimpleName());

                registry.registerBeanDefinition(beanName, beanDefinition);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
