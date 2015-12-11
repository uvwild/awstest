package org.funtime.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * Created by uv on 10/12/2015 for awstest
 */
@Configuration
@EnableAutoConfiguration
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(ConfigServerApplication.class);
//                .properties("spring.config.name=configserver")
        Set<Object> source = springApplicationBuilder.application().getSources();
        springApplicationBuilder.run(args);
    }

}
