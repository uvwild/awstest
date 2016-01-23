package org.funtime.services;

import org.funtime.data.LatLngValueMap;
import org.funtime.data.TimedLatLngValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;

/**
 * Created by uv on 23.01.2016 for awstest
 * create records and retrieve last records for testing
 */
@RestController
public class TestController {

    @Autowired
    AccelerometerPersistenceService accelerometerPersistenceService;
    public static long defaultDelayMillis = 1;

    @RequestMapping(value = "/createNext", method = RequestMethod.GET)
    public ResponseEntity<?> createNext() {
        long now = new Date().getTime();
        boolean existed = accelerometerPersistenceService.put(now, LatLngValueMap.defaultMap);
        TimedLatLngValueMap latLngValueMap = accelerometerPersistenceService.getMapping(now);
        return ResponseEntity.ok(latLngValueMap);
    }

    @RequestMapping(value = "/getLast", method = RequestMethod.GET)
    public ResponseEntity<?> getLastDataset() {
        TimedLatLngValueMap result = accelerometerPersistenceService.getAll();
        Long when = Collections.max(result.keySet());
        return ResponseEntity.ok(result.get(when));
    }

    @RequestMapping(value = "/delay", method = RequestMethod.GET)
    public ResponseEntity<?> delay(@RequestParam(required = false) Long delayMilliSeconds) {
        if (delayMilliSeconds == null) {
            delayMilliSeconds = defaultDelayMillis;
        }
        try {
            Thread.sleep(delayMilliSeconds);
        } catch (InterruptedException e) {
            // nothing to do really
        }
        return ResponseEntity.ok(String.format("delay: $d", delayMilliSeconds));
    }

}
