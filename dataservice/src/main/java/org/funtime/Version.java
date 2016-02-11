package org.funtime;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by uv on 04.02.2016 for awstest
 */

@Getter
@Configuration("version")
@PropertySource("classpath:version.properties")
public class Version {

    @Value("${buildNumber?:'0815'}")
    private String buildNumber;

    @Value("${timeStamp?:'noTimeStamp'}")
    private String timeStamp;

    @Value("${simpleRef?:'noSimpleRef'}")
    private String simpleRef;

    private String staticProperty = "staticProperty";

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
