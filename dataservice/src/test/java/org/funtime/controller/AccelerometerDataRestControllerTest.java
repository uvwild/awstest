package org.funtime.controller;

import org.funtime.data.StaticData;
import org.funtime.services.AccelerometerPersistenceService;
import org.funtime.testing.IntegrationTestApplication;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by uv on 08/12/2015 for awstest
 */
@SpringApplicationConfiguration(classes = IntegrationTestApplication.class)
public class AccelerometerDataRestControllerTest extends StaticData {

    AccelerometerDataRestController accelerometerDataRestController;
    AccelerometerPersistenceService accelerometerPersistenceService;
    HttpServletRequest request;


    @Before
    public void setUp() throws Exception {
        accelerometerDataRestController = new AccelerometerDataRestController();
        accelerometerPersistenceService = mock(AccelerometerPersistenceService.class);
        request = mock(HttpServletRequest.class);
        when(request.getRequestURL()).thenReturn(new StringBuffer("/testURL"));

        when(accelerometerPersistenceService.get(testdate)).thenReturn(latLngValueTestMap);
        when(accelerometerPersistenceService.put(testdate, latLngValueTestMap)).thenReturn(true);
        accelerometerDataRestController.accelerometerPersistenceService = accelerometerPersistenceService;
    }

    @Test
    public void testGetDataset() throws Exception {
        ResponseEntity responseEntity = accelerometerDataRestController.getDataset(testdate);
        verify(accelerometerPersistenceService).get(testdate);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testPutDataset() throws Exception {
        ResponseEntity responseEntity = accelerometerDataRestController.putDataset(testdate, latLngValueTestMap, request);
        verify(accelerometerPersistenceService).put(testdate, latLngValueTestMap);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }
}
