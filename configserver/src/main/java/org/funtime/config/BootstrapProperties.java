package org.funtime.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;


//@Profile("local")
@Component
@Configuration
@ConfigurationProperties(locations = "classpath:/bootstrap.yml", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Getter @Setter
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

    /**
     * Property placeholder configurer needed to process @Value annotations
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}

