package org.funtime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by uv on 04.02.2016 for awstest
 */
@Configuration
public class ApiDocumentationConfiguration {
    @Bean
    public Docket documentation() {
        return new Docket(DocumentationType.SPRING_WEB)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/api/.*"))
                .build()
                .pathMapping("/")
                .apiInfo(metadata());
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfiguration.DEFAULT;
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("My awesome Dataset API")
                .description("to put and read the motion measurements")
                .version("1.0")
                .contact("my-email@domain.org")
                .build();
    }
}
