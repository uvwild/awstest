package org.funtime.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by uv on 10/12/2015 for awstest
 * SpringBootServletInitializer added to run this in a eb container
 */
@Profile("default")
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication extends SpringBootServletInitializer
{
    static Logger LOG = Logger.getLogger(ConfigServerApplication.class.getName());

    @Override
    protected SpringApplicationBuilder configure (SpringApplicationBuilder builder){
        LOG.log(Level.FINE, "configure " + builder);
        return builder.sources(BootstrapProperties.class, ConfigServerApplication.class, StatusController.class);
    }

    public static void main(String[] args) {
        LOG.log(Level.FINE, "main");

        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(ConfigServerApplication.class);
//                .properties("spring.config.name=configserver")
        Set<Object> source = springApplicationBuilder.application().getSources();
        springApplicationBuilder.run(args);
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {

        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        int port = 8080;
        LOG.log(Level.FINE, "port: " + port);
        factory.setPort(port);
        factory.setSessionTimeout(10, TimeUnit.MINUTES);
        //factory.addErrorPages(new ErrorPage(HttpStatus.404, "/notfound.html"));
        return factory;
    }

}
