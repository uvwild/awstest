package org.funtime.services;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
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

    @RequestMapping(value = "/{when}", method = RequestMethod.GET)
    public ResponseEntity<?> getDataset(@PathVariable long when) {
        HashMap result = accelerometerPersistenceService.get(when);
        return (result != null) ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{when}", method = RequestMethod.POST)
    public ResponseEntity putDataset(@PathVariable long when, @RequestBody HashMap data, HttpServletRequest request) throws URISyntaxException {
        String path = request.getRequestURL().toString();


        if (when > 0 && data != null) {
            boolean written = accelerometerPersistenceService.put(when, data);
            return written?ResponseEntity.created(new URI(path)).build():ResponseEntity.status(406).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    static class DataSet{
        @Id
        long date;
        HashMap dataSet;

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof DataSet) && EqualsBuilder.reflectionEquals(this, obj);
        }
    }
}
