package org.funtime.services;

import org.funtime.AwstestApplicationTests;
import org.funtime.config.StaticData;
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
@SpringApplicationConfiguration(classes = AwstestApplicationTests.class)
public class AccelerometerDataRestServiceTest extends StaticData {

    AccelerometerDataRestService accelerometerDataRestService;
    AccelerometerPersistenceService accelerometerPersistenceService;
    HttpServletRequest request;


    @Before
    public void setUp() throws Exception {
        accelerometerDataRestService = new AccelerometerDataRestService();
        accelerometerPersistenceService = mock(AccelerometerPersistenceService.class);
        request = mock(HttpServletRequest.class);
        when(request.getRequestURL()).thenReturn(new StringBuffer("/testURL"));

        when(accelerometerPersistenceService.get(testdate)).thenReturn(latLngValueTestMap);
        when(accelerometerPersistenceService.put(testdate, latLngValueTestMap)).thenReturn(true);
        accelerometerDataRestService.accelerometerPersistenceService = accelerometerPersistenceService;
    }

    @Test
    public void testGetDataset() throws Exception {
        ResponseEntity responseEntity = accelerometerDataRestService.getDataset(testdate);
        verify(accelerometerPersistenceService).get(testdate);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testPutDataset() throws Exception {
        ResponseEntity responseEntity = accelerometerDataRestService.putDataset(testdate, latLngValueTestMap, request);
        verify(accelerometerPersistenceService).put(testdate, latLngValueTestMap);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }
}
