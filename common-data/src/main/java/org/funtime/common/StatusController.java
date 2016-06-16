package org.funtime.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by uv on 24.01.2016 for awstest
 */
public class StatusController {

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("HealthCheck called at " + new Date().toString());
    }

    @RequestMapping(value = "/threads", method = RequestMethod.GET)
    public ResponseEntity<String> showThreads() {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threads =  threadSet.toArray(new Thread[threadSet.size()]);
        String text = Arrays.asList(threads).stream().map(Object::toString).collect(Collectors.joining("\n "));
        return ResponseEntity.ok(String.format("ThreadCount: %d \n%s",threads.length, text));
    }

}
