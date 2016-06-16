package org.funtime.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by uv on 13.06.2016 for awstest
 */
@Configuration
public class DatasourceConfiguration {
    Log logger = LogFactory.getLog(this.getClass());

    @Value("${threadpool.size}")
    @Getter @Setter
    Integer threadPoolSize;

    @Value(value = "${dataservice.protocol}")
    private String dataServiceProtocol;
    @Value(value = "${dataservice.hostname}")
    private String dataServiceHostname;
    @Value(value = "${dataservice.port}")
    private Integer dataServicePort;
    @Value(value = "${dataservice.path}")
    private String dataServicePath;

    @Autowired
    ObjectMapper myConfiguredObjectMapper;

    @Bean
    ScheduledExecutorService createExecutorService() {
        return Executors.newScheduledThreadPool(threadPoolSize);
    }

    /**
     * @return the webclient using our special configured objectmapper for the google key type
     */
    @Bean
    DataServiceWebClient createWebClient() {
        URL target = null;
        try {
            target = new URL(dataServiceProtocol, dataServiceHostname, dataServicePort, dataServicePath);
        } catch (MalformedURLException e) {
            logger.error("check configuration values", e);
        }
        return HystrixFeign.builder().contract(new SpringMvcContract()).encoder(new JacksonEncoder(myConfiguredObjectMapper)).decoder(new JacksonDecoder(
                myConfiguredObjectMapper)).target(DataServiceWebClient.class, target.toString());
    }


    /**
     * add our tuned objectMapper to the feign client also
     * @return
     */
    @Bean
    public Decoder feignDecoder() {
        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(myConfiguredObjectMapper);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

}
