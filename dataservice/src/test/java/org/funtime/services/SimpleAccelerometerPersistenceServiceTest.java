package org.funtime.services;

import org.funtime.data.LatLngConstants;
import org.funtime.data.LatLngValueMap;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by uv on 08/12/2015 for awstest
 */
public class SimpleAccelerometerPersistenceServiceTest implements LatLngConstants {

    SimpleAccelerometerPersistenceServiceImpl service = new SimpleAccelerometerPersistenceServiceImpl();

    @Test
    public void putTest() {
        service.put(1l, defaultMap);
        assertEquals(true, service.has(1l));

    }

    @Test
    public void hasTest() {
        boolean result = service.has(0l);
        assertEquals(true, result);
    }

    @Test
    public void getTest() {
        LatLngValueMap latLngValueMap =  service.get(0l);
//        System.err.print(latLngValueMap); System.err.print(" === "); System.err.println(LatLngValueMap.defaultMap);
        assertEquals(latLngValueMap, LatLngValueMap.defaultMap);

        service.put(1l, LatLngValueMap.defaultMap);
        latLngValueMap =  service.get(1l);
        assertEquals(latLngValueMap, LatLngValueMap.defaultMap);
    }

    void dumm () {
        String.valueOf(new Date().getTime());
    }
}
