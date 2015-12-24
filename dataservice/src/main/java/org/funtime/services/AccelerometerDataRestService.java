package org.funtime.services;

import org.funtime.data.LatLngValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by uv on 08/12/2015 for awstest
 */
@RestController
@RequestMapping(AccelerometerDataRestService.entryPoint)
public class AccelerometerDataRestService {

    static public final String entryPoint = "/dataset";

    @Autowired
    AccelerometerPersistenceService accelerometerPersistenceService;

    @RequestMapping(value = "/{when}", method = RequestMethod.GET)
    public ResponseEntity<?> getDataset(@PathVariable long when) {
        LatLngValueMap result = accelerometerPersistenceService.get(when);
        return (result != null) ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{when}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> putDataset(@PathVariable long when, @RequestBody LatLngValueMap data, HttpServletRequest request) throws URISyntaxException {
        String path = request.getRequestURL().toString();


        if (when > 0 && data != null) {
            boolean written = accelerometerPersistenceService.put(when, data);
            return written?ResponseEntity.created(new URI(path)).build():ResponseEntity.status(406).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
