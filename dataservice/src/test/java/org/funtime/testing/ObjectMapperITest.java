package org.funtime.testing;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.funtime.data.LatLngValueMap;
import org.funtime.data.TimedLatLngValueMap;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by uv on 08/12/2015 for awstest
 * for objectmapper test we dont need to start the services so no reference to DataserviceApplication needed
 */
public class ObjectMapperITest extends org.funtime.testing.AbstractIntegrationTest {

    @Test
    public void testSerialize() throws JsonProcessingException {
        assertNotNull(this.objectMapper);
        String json = this.objectMapper.writeValueAsString(latLngValueTestMap);

        // TODO assertSame is breaking here --- investigate
        assertEquals(latLngValueTestMapJson, json);

        String json2 = this.objectMapper.writeValueAsString(timedLatLngValueTestMap);
        assertEquals(timedLatLngValueTestMapJson, json2);
    }

    @Test
    public void testDeserialize() throws IOException {
        assertNotNull(this.objectMapper);
        LatLngValueMap latLngValueMap = this.objectMapper.readValue(latLngValueTestMapJson, LatLngValueMap.class);
        assertEquals(latLngValueMap, latLngValueTestMap);

        TimedLatLngValueMap timedLatLngValueMap = this.objectMapper.readValue(timedLatLngValueTestMapJson, TimedLatLngValueMap.class);
        assertEquals(timedLatLngValueMap, timedLatLngValueTestMap);

    }

}
