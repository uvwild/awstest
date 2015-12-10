package org.funtime.services;

import com.google.android.gms.maps.model.LatLng;
import org.funtime.AwstestApplicationTests;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by uv on 08/12/2015 for awstest
 */
@SpringApplicationConfiguration(classes = AwstestApplicationTests.class)
public class AccelerometerDataRestServiceITest extends AbstractIntegrationTest {

    AccelerometerDataRestService accelerometerDataRestService;
    AccelerometerPersistenceService accelerometerPersistenceService;

    static long testdate = 123467890;
    static LatLng testCoord = new LatLng(52.f, 13.f);
    static String testString = "13,12,15,14";
    static String restServiceUrl = String.format("%s/%d", AccelerometerDataRestService.entryPoint, testdate);

    static HashMap testData;
    static HashMap testHashMap;

    static {
        testData = new HashMap();
        testData.put(testCoord, testString);
        testHashMap = new HashMap();
        testHashMap.put(testdate, testData);
    }

    @Test
    public void testGetDataset() throws Exception {
        HashMap resultMap = getEntity(restServiceUrl, HashMap.class);
        assertNotNull(resultMap);
        assertTrue(testHashMap.equals(resultMap));
    }

    @Test
    public void testPutDataset() throws Exception {
        ResponseEntity responseEntity = postEntity(restServiceUrl, ResponseEntity.class, testHashMap);
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.CREATED));
    }

//    @Test
//    public void canFetchTestData() {
//        Long testDate = testdate;
//
//        when().
//                get("/characters/{id}", testDate).
//                then().
//                statusCode(HttpStatus.OK).
//                //        body("name", Matchers.is("Mickey Mouse")).
//                body("id", Matchers.is(testdate));
//
//    }

}
