package org.funtime.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.funtime.AwstestApplicationTests;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
// Your spring configuration class containing the @EnableAutoConfiguration annotation
@SpringApplicationConfiguration(classes = AwstestApplicationTests.class)
// Makes sure the application starts at a random free port, caches it throughout all unit tests, and closes it again at the end.
@IntegrationTest("server.port:0")
@WebAppConfiguration
@Configuration
@ComponentScan
@EnableAutoConfiguration
public abstract class AbstractIntegrationTest {

    // Will contain the random free port number
    @Value("${local.server.port}")
    private int port;

    /**
     * Returns the base url for your rest interface
     *
     * @return
     */
    private String getBaseUrl() {
        return "http://localhost:" + port;
    }

    // Some convenience methods to help you interact with your rest interface

    /**
     * @param requestMappingUrl             should be exactly the same as defined in your RequestMapping
     *                                      value attribute (including the parameters in {})
     *                                      RequestMapping(value = yourRestUrl)
     * @param serviceReturnTypeClass        should be the the return type of the service
     * @param parametersInOrderOfAppearance should be the parameters of the requestMappingUrl ({}) in
     *                                      order of appearance
     * @return the result of the service, or null on error
     */
    protected <T> T getEntity(final String requestMappingUrl, final Class<T> serviceReturnTypeClass, final Object... parametersInOrderOfAppearance) {
        // Make a rest template do do the service call
        final TestRestTemplate restTemplate = new TestRestTemplate();
        // Add correct headers, none for this example
        final HttpEntity<String> requestEntity = new HttpEntity<String>(new HttpHeaders());
        try {
            // Do a call the the url
            final ResponseEntity<T> entity = restTemplate.exchange(getBaseUrl() + requestMappingUrl, HttpMethod.GET, requestEntity, serviceReturnTypeClass,
                    parametersInOrderOfAppearance);
            // Return result
            return entity.getBody();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @param requestMappingUrl             should be exactly the same as defined in your RequestMapping
     *                                      value attribute (including the parameters in {})
     *                                      RequestMapping(value = yourRestUrl)
     * @param serviceListReturnTypeClass    should be the the generic type of the list the service
     *                                      returns, eg: List<serviceListReturnTypeClass>
     * @param parametersInOrderOfAppearance should be the parameters of the requestMappingUrl ({}) in
     *                                      order of appearance
     * @return the result of the service, or null on error
     */
    protected <T> List<T> getList(final String requestMappingUrl, final Class<T> serviceListReturnTypeClass, final Object... parametersInOrderOfAppearance) {
        final ObjectMapper mapper = new ObjectMapper();
        final TestRestTemplate restTemplate = new TestRestTemplate();
        final HttpEntity<String> requestEntity = new HttpEntity<String>(new HttpHeaders());
        try {
            // Retrieve list
            final ResponseEntity<List> entity = restTemplate.exchange(getBaseUrl() + requestMappingUrl, HttpMethod.GET, requestEntity, List.class, parametersInOrderOfAppearance);
            final List<Map<String, String>> entries = entity.getBody();
            final List<T> returnList = new ArrayList<T>();
            for (final Map<String, String> entry : entries) {
                // Fill return list with converted objects
                returnList.add(mapper.convertValue(entry, serviceListReturnTypeClass));
            }
            return returnList;
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @param requestMappingUrl      should be exactly the same as defined in your RequestMapping
     *                               value attribute (including the parameters in {})
     *                               RequestMapping(value = yourRestUrl)
     * @param serviceReturnTypeClass should be the the return type of the service
     * @param objectToPost           Object that will be posted to the url
     * @return
     */
    protected <T> T postEntity(final String requestMappingUrl, final Class<T> serviceReturnTypeClass, final Object objectToPost) {
        final TestRestTemplate restTemplate = new TestRestTemplate();
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final HttpEntity<String> requestEntity = new HttpEntity<String>(mapper.writeValueAsString(objectToPost));
            final ResponseEntity<T> entity = restTemplate.postForEntity(getBaseUrl() + requestMappingUrl, requestEntity, serviceReturnTypeClass);
            return entity.getBody();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
