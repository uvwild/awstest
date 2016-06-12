package org.funtime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * extend from servlet initializer so this runs also on a tomcat (cloud)
 */
@SpringBootApplication
@EnableEurekaClient
public class DataserviceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DataserviceApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DataserviceApplication.class, args);
    }

}
