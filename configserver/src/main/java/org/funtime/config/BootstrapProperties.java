package org.funtime.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;


// @Component  // included already in @Configuration
@Configuration
@ConfigurationProperties(locations = "classpath:/bootstrap.yml", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Getter
@Setter
public class BootstrapProperties {

    private String dummy;

    private Server server;

    private Management management;

    @Getter
    @Setter
    public static class Server {
        private Integer port;
    }

    @Getter
    @Setter
    public static class Management {
        private String contextPath;
    }


    @Autowired
    ConfigurableListableBeanFactory beanFactory;

    /**
     * Property placeholder configurer needed to process @Value annotations - do it static to satisfy bean factory
     */
    @Bean
    static public PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("bootstrap.yml"));
        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
//        propertySourcesPlaceholderConfigurer.postProcessBeanFactory(beanFactory);
//        properties = propertySourcesPlaceholderConfigurer.getAppliedPropertySources().get(PropertySourcesPlaceholderConfigurer.LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME);
        return propertySourcesPlaceholderConfigurer;
    }

    @PostConstruct
    public void init() {
        System.out.println("================== dummy: " + dummy + "================== ");
    }


}

