package org.funtime.controller;

import org.funtime.DataserviceApplication;
import org.funtime.data.LatLngConstants;
import org.funtime.data.LatLngValueMap;
import org.funtime.services.AccelerometerPersistenceService;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by uv on 08/12/2015 for awstest
 * we need to reference the rest service applicationso the services start properly
 */
@SpringApplicationConfiguration(classes = DataserviceApplication.class)
public class AccelerometerDataRestControllerITest extends org.funtime.testing.AbstractIntegrationTest {

    AccelerometerDataRestController accelerometerDataRestController;
    AccelerometerPersistenceService accelerometerPersistenceService;

    static String restServicePathTestDate = String.format("%s/%d", AccelerometerDataRestController.entryPoint, testdate);
    static String restServicePathDefault = String.format("%s/%d", AccelerometerDataRestController.entryPoint, 0);

    @Test
    public void testGetDatasetZero() throws Exception {
        LatLngValueMap resultMap = getEntity(restServicePathDefault, LatLngValueMap.class);
        assertNotNull(resultMap);
        assertEquals(LatLngConstants.defaultMap, resultMap);
    }

    @Test
    public void testGetDataset() throws Exception {
        ResponseEntity<Void> responseEntity = postEntity(restServicePathTestDate, Void.class, latLngValueTestMap);
        LatLngValueMap resultMap = getEntity(restServicePathTestDate, LatLngValueMap.class);
        assertNotNull(resultMap);
        assertEquals(latLngValueTestMap, resultMap);
    }

    @Test
    public void testPutDataset() throws Exception {
        ResponseEntity<Void> responseEntity = postEntity(restServicePathTestDate, Void.class, latLngValueTestMap);
        assertNotNull(responseEntity);
        assertSame(responseEntity.getStatusCode(),HttpStatus.CREATED);
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
