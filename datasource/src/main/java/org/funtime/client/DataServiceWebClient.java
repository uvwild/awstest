package org.funtime.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.funtime.data.LatLngValueMap;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by uv on 13.06.2016 for awstest
 * http://stackoverflow.com/questions/35853908/how-to-set-custom-jackson-objectmapper-with-spring-cloud-netflix-feign
 */
//@FeignClient("datasource")
@RibbonClient(value = "datasource", configuration = DatasourceConfiguration.class)
public interface DataServiceWebClient {

    @RequestMapping(value = "/dataset/{when}", method = RequestMethod.POST)
    @RequestLine("POST /dataset/{when}")
    @Headers("Content-Type: application/json")
    void sendData(@PathVariable("when") @Param("when") Long when, LatLngValueMap latLngValueMap);


    @RequestMapping(value = "/dataset/{when}", method = RequestMethod.GET)
    @RequestLine("GET /dataset/{when}")
    @Headers("Accept: application/json")
    LatLngValueMap readData(@PathVariable("when") @Param("when") Long when);
}
