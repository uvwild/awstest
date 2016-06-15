package org.funtime.client;

import org.funtime.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by uv on 22.01.2016 for awstest
 * this URL can be configured for AWS elastic load balancer
 */
@RestController
public class StatusController {

    @Autowired
    DatasourceConfiguration configuration;

    @Autowired
    ScheduledExecutorService executorService;

    @Autowired
    Version version;

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("HealthCheck called at " + new Date().toString() + " for Version.bn: " + version.getBuildNumber() + " for Version.ts: " + version.getTimeStamp());
    }

    @RequestMapping(value = "/threads", method = RequestMethod.GET)
    public ResponseEntity<String> showThreads() {
        return ResponseEntity.ok(String.format("PoolsSize: %d  %s\n%s",
                                               configuration.threadPoolSize,
                                               (executorService.isTerminated() ? "TERM" : "NOTERM"),
                                               (executorService.isShutdown() ? "SHUT" : "NOSHUT"),
                                               executorService.toString()));
    }

}
