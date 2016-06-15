package org.funtime;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by uv on 04.02.2016 for awstest
 */

@Getter
@Configuration("version")                           // use a specific ns
@PropertySource("classpath:version.properties")     // use annotation instead of adding the p-source in the PSPC ctor below
public class Version {

    // dont use elvis operator here since it requires a boolean expression!
    @Value("${version.buildNumber:'0815'}")
    private String buildNumber;

    @Value("${version.timeStamp:'noSimpleRef'}")
    private String timeStamp;

    //    http://stackoverflow.com/questions/31914529/what-configuration-enables-the-evaluation-of-value-annotations
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
}
