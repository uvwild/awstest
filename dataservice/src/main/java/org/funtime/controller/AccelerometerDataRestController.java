package org.funtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.funtime.data.LatLngValueMap;
import org.funtime.data.TimedLatLngValueMap;
import org.funtime.services.AccelerometerPersistenceService;
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
@RequestMapping(AccelerometerDataRestController.entryPoint)
public class AccelerometerDataRestController {

    static public final String entryPoint = "/dataset";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public AccelerometerPersistenceService accelerometerPersistenceService;

    @ApiOperation(value = "getAll", nickname = "getAll", notes = "read all data!",response = TimedLatLngValueMap.class)
    @RequestMapping(value = { "" , "/"}, method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        TimedLatLngValueMap result = accelerometerPersistenceService.getAll();
        return (result != null) ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "get", notes = "getOne", response = LatLngValueMap.class)
    @RequestMapping(value = "/{when}", method = RequestMethod.GET)
    public ResponseEntity<?> getDataset(@PathVariable long when) {
        LatLngValueMap result = accelerometerPersistenceService.get(when);
        return (result != null) ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }


    @ApiOperation(value = "getObject", notes = "get a json", response = String.class)
    @RequestMapping(value = "/object/{when}", method = RequestMethod.GET, produces = "application/json")
    public LatLngValueMap getObject(@PathVariable long when) {
        LatLngValueMap result = accelerometerPersistenceService.get(when);
        return result;
    }

    @ApiOperation(value = "putObject", notes = "putOneObject")
    @RequestMapping(value = "/object/{when}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LatLngValueMap putObject(@PathVariable long when, @RequestBody LatLngValueMap data, HttpServletRequest request) throws URISyntaxException {
        String path = request.getRequestURL().toString();

        if (data != null) {
            boolean existed = accelerometerPersistenceService.put(when, data);
            return data;
//            return existed?ResponseEntity.created(new URI(path)).build():ResponseEntity.ok().build();
        } else {
            return null;
        }
    }

    @ApiOperation(value = "put", notes = "putOne")
    @RequestMapping(value = "/{when}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> putDataset(@PathVariable long when, @RequestBody LatLngValueMap data, HttpServletRequest request) throws URISyntaxException {
        String path = request.getRequestURL().toString();

        if (when > 0 && data != null) {
            boolean existed = accelerometerPersistenceService.put(when, data);
            return ResponseEntity.created(new URI(path)).build();
//            return existed?ResponseEntity.created(new URI(path)).build():ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().header("Reason", String.format("called with when=%d and body=%s",when,data)).build();
        }
    }

//    public TimedLatLngValueMap getRecord (long date) {
//        LatLngValueMap latLngValueMap = accelerometerPersistenceService.get(date);
//        return new TimedLatLngValueMap(date, latLngValueMap);
//    }
//    public boolean putRecord(TimedLatLngValueMap data){
//        return false;
//    }

}
