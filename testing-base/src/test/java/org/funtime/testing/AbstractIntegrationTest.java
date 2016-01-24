package org.funtime.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.funtime.data.CustomObjectMapper;
import org.funtime.data.StaticData;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
// Your spring configuration class containing the @EnableAutoConfiguration annotation
@SpringApplicationConfiguration(classes = { IntegrationTestApplication.class, CustomObjectMapper.class })
// Makes sure the application starts at a random free port, caches it throughout all unit tests, and closes it again at the end.
@IntegrationTest("server.port:0")    // important to configure the rest service port correctly
@WebAppConfiguration                // startup the web server
@ComponentScan(basePackages = { "org.funtime.data", "órg.funtime.config" })
public abstract class AbstractIntegrationTest extends StaticData {

    final TestRestTemplate restTemplate = new TestRestTemplate();

    // Will contain the random free port number
    @Value("${local.server.port}")
    private int port;

    public int getLocalPort() {
        return this.port;
    }

    @Autowired
    ObjectMapper objectMapper;      // my custom Mapper is injected

    @Before
    public void setupTestObjectMapper() {
        for (HttpMessageConverter<?> converter : restTemplate.getMessageConverters()) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = (MappingJackson2HttpMessageConverter) converter;
                jackson2HttpMessageConverter.setObjectMapper(objectMapper);
            }
        }
    }

    public String getBaseUrl() {
        return "http://localhost:" + port;
    }

    public String getFullUrl(String rest) {
        return "http://localhost:" + port + rest;
    }

    // Some convenience methods to help you interact with your rest interface

    /**
     * @param requestMappingUrl             should be exactly the same as defined in your RequestMapping value attribute (including the parameters in {})
     *                                      RequestMapping(value = yourRestUrl)
     * @param serviceReturnTypeClass        should be the the return type of the service
     * @param parametersInOrderOfAppearance should be the parameters of the requestMappingUrl ({}) in order of appearance
     * @return the result of the service, or null on error
     */
    protected <T> T getEntity(final String requestMappingUrl, final Class<T> serviceReturnTypeClass, final Object... parametersInOrderOfAppearance) {
        // Add correct headers, none for this example
        final HttpEntity<String> requestEntity = new HttpEntity<String>(new HttpHeaders());
        try {
            // Do a call the the url
            final ResponseEntity<T> entity = restTemplate.exchange(getFullUrl(requestMappingUrl),
                                                                   HttpMethod.GET,
                                                                   requestEntity,
                                                                   serviceReturnTypeClass,
                                                                   parametersInOrderOfAppearance);
            // Return result
            return entity.getBody();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @param requestMappingUrl             should be exactly the same as defined in your RequestMapping value attribute (including the parameters in {})
     *                                      RequestMapping(value = yourRestUrl)
     * @param serviceListReturnTypeClass    should be the the generic type of the list the service returns, eg: List<serviceListReturnTypeClass>
     * @param parametersInOrderOfAppearance should be the parameters of the requestMappingUrl ({}) in order of appearance
     * @return the result of the service, or null on error
     */
    protected <T> List<T> getList(final String requestMappingUrl, final Class<T> serviceListReturnTypeClass, final Object... parametersInOrderOfAppearance) {
        final HttpEntity<String> requestEntity = new HttpEntity<String>(new HttpHeaders());
        try {
            // Retrieve list
            final ResponseEntity<List> entity = restTemplate.exchange(getFullUrl(requestMappingUrl),
                                                                      HttpMethod.GET,
                                                                      requestEntity,
                                                                      List.class,
                                                                      parametersInOrderOfAppearance);
            final List<Map<String, String>> entries = entity.getBody();
            final List<T> returnList = new ArrayList<T>();
            for (final Map<String, String> entry : entries) {
                // Fill return list with converted objects
                returnList.add(objectMapper.convertValue(entry, serviceListReturnTypeClass));
            }
            return returnList;
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @param requestMappingUrl      should be exactly the same as defined in your RequestMapping value attribute (including the parameters in {})
     *                               RequestMapping(value = yourRestUrl)
     * @param serviceReturnTypeClass should be the the return type of the service
     * @param objectToPost           Object that will be posted to the url
     * @return
     */
    protected <T> ResponseEntity<T> postEntity(final String requestMappingUrl, final Class<T> serviceReturnTypeClass, final Object objectToPost) {
        final TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final HttpEntity<String> requestEntity = new HttpEntity<String>(mapper.writeValueAsString(objectToPost), headers);
            final ResponseEntity<T> entity = restTemplate.postForEntity(getFullUrl(requestMappingUrl),requestEntity, serviceReturnTypeClass);
            return entity;
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
