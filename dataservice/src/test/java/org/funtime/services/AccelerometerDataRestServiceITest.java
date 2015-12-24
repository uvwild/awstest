package org.funtime.services;

import org.funtime.AwstestApplicationTests;
import org.funtime.data.LatLngValueMap;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by uv on 08/12/2015 for awstest
 */
@SpringApplicationConfiguration(classes = AwstestApplicationTests.class)
public class AccelerometerDataRestServiceITest extends AbstractIntegrationTest {

    AccelerometerDataRestService accelerometerDataRestService;
    AccelerometerPersistenceService accelerometerPersistenceService;

    static String restServiceUrl = String.format("%s/%d", AccelerometerDataRestService.entryPoint, testdate);

    @Test
    public void testGetDataset() throws Exception {
        LatLngValueMap resultMap = getEntity(restServiceUrl, LatLngValueMap.class);
        assertNotNull(resultMap);
        assertEquals(latLngValueTestMap, resultMap);
    }

    @Test
    public void testPutDataset() throws Exception {
        ResponseEntity<Void> responseEntity = postEntity(restServiceUrl, Void.class, latLngValueTestMap);
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
