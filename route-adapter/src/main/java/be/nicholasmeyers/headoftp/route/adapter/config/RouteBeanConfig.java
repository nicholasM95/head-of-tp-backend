package be.nicholasmeyers.headoftp.route.adapter.config;

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
public class RouteBeanConfig {
    @Bean
    static BeanFactoryPostProcessor routeBeanFactoryPostProcessor(ApplicationContext beanRegistry) {
        return beanFactory ->
                routeApplicationContext(
                        (BeanDefinitionRegistry) ((GenericApplicationContext) beanRegistry)
                                .getBeanFactory());
    }

    static void routeApplicationContext(BeanDefinitionRegistry beanRegistry) {
        ClassPathBeanDefinitionScanner beanDefinitionScanner = new ClassPathBeanDefinitionScanner(beanRegistry);
        beanDefinitionScanner.addIncludeFilter(addUseCaseFilter());
        beanDefinitionScanner.scan(
                "be.nicholasmeyers.headoftp.route.usecase",
                "be.nicholasmeyers.headoftp.route.domain"
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
