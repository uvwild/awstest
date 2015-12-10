package org.funtime.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;

/**
 * Created by uv on 10/12/2015 for awstest
 */
@Configuration
@EnableAutoConfiguration
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigServerApplication.class)
                /*.properties("spring.config.name=configserver")*/
                .run(args);
    }

}
