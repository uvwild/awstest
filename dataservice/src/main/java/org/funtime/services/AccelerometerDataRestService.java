package org.funtime.services;

import org.funtime.data.LatLngValueMap;
import org.funtime.data.TimedLatLngValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * Created by uv on 08/12/2015 for awstest
 */
@RestController
@RequestMapping(AccelerometerDataRestService.entryPoint)
public class AccelerometerDataRestService {

    static public final String entryPoint = "/dataset";

    @Autowired
    AccelerometerPersistenceService accelerometerPersistenceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        HashMap<Long,LatLngValueMap> result = accelerometerPersistenceService.getAll();
        return (result != null) ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{when}", method = RequestMethod.GET)
    public ResponseEntity<?> getDataset(@PathVariable long when) {
        LatLngValueMap result = accelerometerPersistenceService.get(when);
        return (result != null) ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{when}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> putDataset(@PathVariable long when, @RequestBody LatLngValueMap data, HttpServletRequest request) throws URISyntaxException {
        String path = request.getRequestURL().toString();

        if (when > 0 && data != null) {
            boolean existed = accelerometerPersistenceService.put(when, data);
            return existed?ResponseEntity.created(new URI(path)).build():ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public TimedLatLngValueMap getRecord (long date) {
        LatLngValueMap latLngValueMap = accelerometerPersistenceService.get(date);
        return new TimedLatLngValueMap(date, latLngValueMap);
    }
    public boolean put(TimedLatLngValueMap data){
        return false;
    }

}
