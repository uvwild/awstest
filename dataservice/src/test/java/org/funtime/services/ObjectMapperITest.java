package org.funtime.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.funtime.AwstestApplicationTests;
import org.funtime.data.LatLngValueMap;
import org.funtime.data.TimedLatLngValueMap;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by uv on 08/12/2015 for awstest
 */
@SpringApplicationConfiguration(classes = AwstestApplicationTests.class)
public class ObjectMapperITest extends AbstractIntegrationTest {

    @Test
    public void testSerialize() throws JsonProcessingException {
        assertNotNull(objectMapper);
        String json = objectMapper.writeValueAsString(latLngValueTestMap);
        assertEquals(latLngValueTestMapJson,json);

        String json2 = objectMapper.writeValueAsString(timedLatLngValueTestMap);
        assertEquals(timedLatLngValueTestMapJson,json2);
    }

    @Test
    public void testDeserialize() throws IOException {
        assertNotNull(objectMapper);
        LatLngValueMap latLngValueMap = objectMapper.readValue(latLngValueTestMapJson, LatLngValueMap.class);
        assertEquals(latLngValueMap, latLngValueTestMap);

        TimedLatLngValueMap timedLatLngValueMap = objectMapper.readValue(timedLatLngValueTestMapJson, TimedLatLngValueMap.class);
        assertEquals(timedLatLngValueMap, timedLatLngValueTestMap);

    }

}
