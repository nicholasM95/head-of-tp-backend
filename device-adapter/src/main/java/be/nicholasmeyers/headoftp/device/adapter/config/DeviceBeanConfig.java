package be.nicholasmeyers.headoftp.device.adapter.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

@Configuration
public class DeviceBeanConfig {
    @Bean
    static BeanFactoryPostProcessor deviceBeanFactoryPostProcessor(ApplicationContext beanRegistry) {
        return beanFactory ->
                deviceApplicationContext(
                        (BeanDefinitionRegistry) ((GenericApplicationContext) beanRegistry)
                                .getBeanFactory());
    }

    static void deviceApplicationContext(BeanDefinitionRegistry beanRegistry) {
        ClassPathBeanDefinitionScanner beanDefinitionScanner = new ClassPathBeanDefinitionScanner(beanRegistry);
        beanDefinitionScanner.addIncludeFilter(addUseCaseFilter());
        beanDefinitionScanner.scan(
                "be.nicholasmeyers.headoftp.device.usecase",
                "be.nicholasmeyers.headoftp.device.domain"
        );
    }

    static TypeFilter addUseCaseFilter() {
        return (MetadataReader mr, MetadataReaderFactory mrf) -> mr.getClassMetadata()
                                                                         .getClassName()
                                                                         .endsWith("UseCase") ||
                                                                 mr.getClassMetadata()
                                                                         .getClassName()
                                                                         .endsWith("Factory");
    }
}
