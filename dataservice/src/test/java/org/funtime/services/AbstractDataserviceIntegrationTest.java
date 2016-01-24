package org.funtime.services;

import org.funtime.testing.AbstractIntegrationTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by uv on 24.01.2016 for awstest
 * add specifics for Config Server testing
 */
public abstract class AbstractDataserviceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    Environment env;

    public Environment getEnv() {
        return env;
    }

    @Autowired
    ConfigurableListableBeanFactory beanFactory;

    @Value("${management.context-path}")
    String parentAdminPath;


    PropertySource properties;
    @Before
    // a complicated way to access the bootstrap.yml file
    // TODO However, this is using the local profile for no understood reason!!!
    public void readBootstrapYaml() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("bootstrap.yml"));
        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
        propertySourcesPlaceholderConfigurer.postProcessBeanFactory(beanFactory);
        properties = propertySourcesPlaceholderConfigurer.getAppliedPropertySources().get(PropertySourcesPlaceholderConfigurer.LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME);
    }

}
