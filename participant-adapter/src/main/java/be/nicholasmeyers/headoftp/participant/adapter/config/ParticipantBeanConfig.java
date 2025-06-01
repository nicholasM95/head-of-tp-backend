package be.nicholasmeyers.headoftp.participant.adapter.config;

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
public class ParticipantBeanConfig {
    @Bean
    static BeanFactoryPostProcessor participantBeanFactoryPostProcessor(ApplicationContext beanRegistry) {
        return beanFactory ->
                participantApplicationContext(
                        (BeanDefinitionRegistry) ((GenericApplicationContext) beanRegistry)
                                .getBeanFactory());
    }

    static void participantApplicationContext(BeanDefinitionRegistry beanRegistry) {
        ClassPathBeanDefinitionScanner beanDefinitionScanner = new ClassPathBeanDefinitionScanner(beanRegistry);
        beanDefinitionScanner.addIncludeFilter(addUseCaseFilter());
        beanDefinitionScanner.scan(
                "be.nicholasmeyers.headoftp.participant.usecase",
                "be.nicholasmeyers.headoftp.participant.domain"
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
