package org.funtime.client.controller;

import org.funtime.Version;
import org.funtime.client.DatasourceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by uv on 22.01.2016 for awstest
 * this URL can be configured for AWS elastic load balancer
 */
@RestController
public class StatusController extends org.funtime.common.StatusController {

    @Autowired
    DatasourceConfiguration configuration;

    @Autowired
    ScheduledExecutorService executorService;

    @Autowired
    Version version;

    @RequestMapping(value = "/executorService", method = RequestMethod.GET)
    public ResponseEntity<String> showExecutorService() {
        return ResponseEntity.ok(String.format("PoolsSize: %d  %s\t%s\n%s",
                                               configuration.getThreadPoolSize(),
                                               (executorService.isTerminated() ? "TERM" : "NOTERM"),
                                               (executorService.isShutdown() ? "SHUT" : "NOSHUT"),
                                                executorService.toString()));
    }
}
