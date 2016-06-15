package org.funtime.testing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.google.android.gms.maps.model.LatLng;
import org.funtime.data.LatLngValueMap;
import org.funtime.data.MyLatLngValueMap;
import org.funtime.data.TimedLatLngValueMap;
import org.funtime.helper.ResponseEntityMixin;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by uv on 08/12/2015 for awstest
 * for objectmapper test we dont need to start the services so no reference to DataserviceApplication needed
 */
@ContextConfiguration(locations = "classpath:test-context.xml")
public class ObjectMapperITest extends AbstractIntegrationTest {

    String responseEntityJson = "{\"headers\":{},\"body\":{\"lat/lng: (52.0,13.0)\":[13,12,15,14]},\"statusCode\":\"OK\"}";

    @Test
    public void testMapperFeatures() {
        Class mixin = objectMapper.findMixInClassFor(ResponseEntity.class);
        assertEquals(ResponseEntityMixin.class, mixin);
    }

    @Test
    public void testSerializeLatLngValueMap() throws JsonProcessingException {
        assertNotNull(this.objectMapper);
        String json = this.objectMapper.writeValueAsString(latLngValueTestMap);
        // TODO assertSame is breaking here --- investigate
        assertEquals(latLngValueTestMapJson, json);

        String json2 = this.objectMapper.writeValueAsString(timedLatLngValueTestMap);
        assertEquals(timedLatLngValueTestMapJson, json2);
    }

    @Test
    public void testDeserializeLatLngValueMap() throws IOException {
        assertNotNull(this.objectMapper);
        LatLngValueMap latLngValueMap = this.objectMapper.readValue(latLngValueTestMapJson, LatLngValueMap.class);
        assertEquals(latLngValueTestMap, latLngValueMap);

        TimedLatLngValueMap timedLatLngValueMap = this.objectMapper.readValue(timedLatLngValueTestMapJson, TimedLatLngValueMap.class);
        assertEquals(timedLatLngValueTestMap, timedLatLngValueMap);
    }

    @Test
    public void testDeserializeMyLatLngValueMap() throws IOException {
        assertNotNull(this.objectMapper);
        MyLatLngValueMap latLngValueMap = this.objectMapper.readValue(latLngValueTestMapJson, MyLatLngValueMap.class);
        assertEquals(latLngValueTestMap.toString().trim(), latLngValueMap.toString().trim());
    }


    @Test
    public void testSerializeResponseEntity() throws IOException {
        assertNotNull(this.objectMapper);
        ResponseEntity<LatLngValueMap> testResponse = new ResponseEntity<>(latLngValueTestMap, HttpStatus.OK);
        String json = this.objectMapper.writeValueAsString(testResponse);
        assertEquals(responseEntityJson, json);
    }

    @Test
    @Ignore
    public void testDeserializeResponseEntity() throws IOException {
        assertNotNull(this.objectMapper);
        JavaType listLongJavaType = this.objectMapper.getTypeFactory().constructCollectionType(List.class, Long.class);
        JavaType latLangJavaType = this.objectMapper.getTypeFactory().constructType(LatLng.class);
        JavaType latLngValueMapJavaType = this.objectMapper.getTypeFactory().constructMapType(HashMap.class, latLangJavaType, listLongJavaType);
        JavaType responseEntityJavaType = this.objectMapper.getTypeFactory().constructParametrizedType(ResponseEntity.class,
                                                                                                       ResponseEntity.class,
                                                                                                       latLngValueMapJavaType);
        ResponseEntity<LatLngValueMap> latLngValueMapResponse = this.objectMapper.readValue(responseEntityJson, responseEntityJavaType);
        assertEquals(responseEntityJson, latLngValueMapResponse);
    }

}
