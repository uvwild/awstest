package org.funtime.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;

import java.util.Set;

/**
 * Created by uv on 10/12/2015 for awstest
 * SpringBootServletInitializer added to run this in a eb container
 */
//@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableConfigServer
public class ConfigServerApplication extends SpringBootServletInitializer
{

    @Override
    protected SpringApplicationBuilder configure (SpringApplicationBuilder builder){
        return builder.sources(ConfigServerApplication.class, BootstrapProperties.class, StatusController.class);
    }

    public static void main(String[] args) {
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(ConfigServerApplication.class);
//                .properties("spring.config.name=configserver")
        Set<Object> source = springApplicationBuilder.application().getSources();
        springApplicationBuilder.run(args);
    }
}
