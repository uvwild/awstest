package org.funtime.services;

import com.google.android.gms.maps.model.LatLng;
import junit.framework.TestCase;
import org.funtime.AwstestApplicationTests;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static org.mockito.Mockito.*;

/**
 * Created by uv on 08/12/2015 for awstest
 */
@SpringApplicationConfiguration(classes = AwstestApplicationTests.class)
public class AccelerometerDataRestServiceTest extends TestCase {

    AccelerometerDataRestService accelerometerDataRestService;
    AccelerometerPersistenceService accelerometerPersistenceService;
    HttpServletRequest request;

    static long testdate = 123467890;
    static LatLng testCoord = new LatLng(52.f,13.f);
    static String testString= "13,12,15,14";
    static HashMap testData;
    static HashMap testHashMap;
    static {
        testData = new HashMap();
        testData.put(testCoord, testString);
        testHashMap = new HashMap();
        testHashMap.put(testdate, testData);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        accelerometerDataRestService = new AccelerometerDataRestService();
        accelerometerPersistenceService = mock(AccelerometerPersistenceService.class);
        request = mock(HttpServletRequest.class);
        when(request.getRequestURL()).thenReturn(new StringBuffer("/testURL"));

        when(accelerometerPersistenceService.get(testdate)).thenReturn(testHashMap);
        when(accelerometerPersistenceService.put(testdate, testData)).thenReturn(true);
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
        ResponseEntity responseEntity = accelerometerDataRestService.putDataset(testdate, testData, request);
        verify(accelerometerPersistenceService).put(testdate, testData);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }
}
