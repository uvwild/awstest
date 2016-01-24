package org.funtime.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by uv on 24.01.2016 for awstest
 */
@RestController
public class StatusController {

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("HealthCheck called at " + new Date().toString());
    }
}
